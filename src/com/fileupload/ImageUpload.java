package com.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.deepak.hibernate.DAO.FilesDAO;
import com.deepak.hibernate.entity.Files;
//import com.deepak.hibernate.entity.Files;

@WebServlet("/ImageUpload")
public class ImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public  String path="E:/images/";
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action=request.getParameter("action");
		
		switch(action)
		{
		case "filesUpload":
			filesUpload(request,response);
			break;
		case "updateInformation":
			updateInformation(request,response);
			break;
			
		default :
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
			
		}
		
	
	}
	

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action=request.getParameter("action");
		
		switch(action)
		{
		
			
		case "listingImages":
			listingImages(request,response);
			break;
			
		case"viewImage" :
			viewImage(request,response);
			break;
		case"deleteImage" :
			deleteImage(request,response);
			break;
			
		default :
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
			
		}
		
	
	}
	
	private void deleteImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//logic for delete image from database
		int fileId=Integer.parseInt(request.getParameter("fileId"));
         Files file= new FilesDAO().getFile(fileId);
		 new FilesDAO().deleteImage(fileId);
		 //logic for delete image from file system
		 File fileOnDisk=new File(path+file.getFileName());
		 if(fileOnDisk.delete())
		 {
			 System.out.println("File got delete from filesystem");
		 }
		 else
		 {
			 System.out.println("File not  delete from filesystem"); 
		 }
		 
		 listingImages(request,response);
	}




	private void viewImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             int fileId=Integer.parseInt(request.getParameter("fileId"));
    
            Files file= new FilesDAO().getFile(fileId);
            request.setAttribute("file",file);
            request.setAttribute("path", path);
            request.getRequestDispatcher("viewImage.jsp").forward(request, response);
            // System.out.println(file);
	}




	private void updateInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int  fileId=Integer.parseInt(request.getParameter("fileId"));
		String label=request.getParameter("label");
		String caption=request.getParameter("caption");
		String fileName=request.getParameter("fileName");
       Files file=new Files(fileId, fileName, label, caption);
        new FilesDAO().updateInformation(fileId,label,caption);
        listingImages(request,response);
        
		
	}

	
	
	
private void listingImages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	List<Files> files=new FilesDAO().listFiles();
	request.setAttribute("files", files);
	request.setAttribute("path", path);
    request.getRequestDispatcher("listFiles.jsp").forward(request, response);

		
	}
public void filesUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	
ServletFileUpload upload=new ServletFileUpload(new DiskFileItemFactory());
	
	try {
		List<FileItem> images=upload.parseRequest(request);
		for(FileItem image:images)
		{
			String name=image.getName();
			try{name=name.substring(name.lastIndexOf("\\")+1);}catch(Exception e) {e.printStackTrace();}
			System.out.println(name);	
			File file=new File(path+name);
			if(!file.exists())
			{
				new FilesDAO().addFileDetails(new Files(name));
				image.write(file);
			}
			
		
		
		}
		
			
	} catch (FileUploadException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	listingImages(request,response);
	
}
}
