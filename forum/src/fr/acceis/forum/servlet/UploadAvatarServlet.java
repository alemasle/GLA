package fr.acceis.forum.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class UploadAvatarServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
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

				if ("".compareTo(fi.getName()) == 0) {
					System.out.println("Fields empty");
					req.setAttribute("error", "emptyfields");
					req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
				} else {

					String path = System.getProperty("user.dir") + "/forum/WebContent/fichiers/";
					String[] tabName = fi.getName().split("\\.");
					String extension = tabName[tabName.length - 1];

					String fileNameTmp = user + "-tmp." + extension;
					String fileName = user + "." + extension;

					File outputFile = new File(path + fileNameTmp);
					InputStream is;
					BufferedImage image;
					OutputStream os;
					try {
						is = fi.getInputStream();
						image = ImageIO.read(is);
						os = new FileOutputStream(outputFile);
						ImageIO.write(image, extension, os);

						File newF = new File(path + fileName);
						outputFile.renameTo(newF);

						System.out.println("--> \"" + user + "\": uploaded a new avatar: " + fileName);

						dao.updateAvatar(user, fileName);

						is.close();
						os.close();

						req.getSession().setAttribute("avatar", dao.getAvatar(user));
						resp.sendRedirect("/forum/profil?login=" + user);

					} catch (Exception exp) {
						outputFile.delete();

						System.out.println("--> " + user + " uploaded: " + fileName
								+ " - ERROR during convert, can be corrupted: REJECTED");
						req.setAttribute("error", "convert");
						req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
						return;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
