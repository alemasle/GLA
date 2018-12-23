package fr.acceis.forum.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadAvatarServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(UploadAvatarServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final long SIZE_MAX = 1000000; // 1Mo

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		session.setAttribute("sourceFrom", "uploadavatar");
		req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String user = (String) session.getAttribute("user");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		String sourceFrom = (String) session.getAttribute("sourceFrom");
		if (!sourceFrom.equals("uploadavatar")) {
			if (!user.equals("invite"))
				logger.warn("\"" + user
						+ "\" tried to upload a new avatar without being in the good page, redirected to home");
			else
				logger.warn("visitor " + req.getRemoteAddr()
						+ " tried to upload a new avatar without being in the good page, redirected to home");
			resp.sendRedirect("/forum/home");
			return;
		}

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			List<FileItem> items = upload.parseRequest(new ServletRequestContext(req));

			if (!items.isEmpty()) {
				FileItem fi = items.get(0);

				if (fi.getSize() > SIZE_MAX) {
					logger.warn(
							"\"" + user + "\" uploaded " + fi.getName() + ", error size of image too big: REJECTED");

					req.setAttribute("error", "tooLarge");
					req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);

				} else {

					if ("".equals(fi.getName())) {
						logger.warn("\"" + user + "\" tried to upload an empty file: REJECTED");
						req.setAttribute("error", "emptyfields");
						req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
					} else {

						String path = System.getProperty("user.dir") + "/forum/WebContent/fichiers/";
						String[] tabName = fi.getName().split("\\.");
						String extension = tabName[tabName.length - 1];
						String[] ext_valables = { "jpg", "png", "gif" };
						boolean extensionFound = false;

						// If the last file extension exists in the list of images extensions then we
						// use it
						for (int k = 0; k < ext_valables.length && !extensionFound; k++)
							if (ext_valables[k].compareTo(extension) == 0)
								extensionFound = true;

						// else we look in all the file name if there is an other image extension
						for (int j = 0; j < ext_valables.length && !extensionFound; j++)
							for (int i = 1; i < tabName.length && !extensionFound; i++)
								if (ext_valables[j].compareTo(tabName[i]) == 0) {
									extensionFound = true;
									extension = tabName[i];
								}

						if (!extensionFound)
							extension = "jpg";

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

							dao.updateAvatar(user, fileName);

							is.close();
							os.close();

							logger.info("\"" + user + "\" uploaded a new avatar: \"" + fileName + "\"");
							req.getSession().setAttribute("avatar", dao.getAvatar(user));
							resp.sendRedirect("/forum/profil?login=" + URLEncoder.encode(user, "UTF-8"));

						} catch (Exception exp) {
							outputFile.delete();
							logger.warn("error while converting the image from \"" + user
									+ "\" , can be corrupted: REJECTED. error: " + exp.getMessage());
							req.setAttribute("error", "convert");
							req.getRequestDispatcher("/WEB-INF/jsp/uploadavatar.jsp").forward(req, resp);
							return;
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("error while \"" + user + "\" tried to upload a new avatar, error: " + e.getMessage());
		}
	}

}
