package fr.acceis.forum.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class UploadAvatarServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session.getAttribute("user") == null) {
			resp.sendRedirect("/forum/home");
		} else {
			req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("user");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			List<FileItem> items = upload.parseRequest(new ServletRequestContext(req));

			if (!items.isEmpty()) {
				FileItem fi = items.get(0);

				String path = System.getProperty("user.dir") + "/forum/WebContent/fichiers/";
				String[] tabName = fi.getName().split("\\.");
				
				for (String string : tabName) {
					System.out.println(string);
				}

				String fileName = user + "." + tabName[tabName.length - 1];

				System.out.println(path + fileName);
				File file = new File(path + fileName);
				fi.write(file);
				
				dao.updateAvatar(user, fileName);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect("/forum/profil?login=" + user);
	}

}
