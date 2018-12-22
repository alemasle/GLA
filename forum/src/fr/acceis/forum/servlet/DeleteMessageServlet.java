package fr.acceis.forum.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteMessageServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(DeleteMessageServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/deletemessage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String user = (String) session.getAttribute("user");
		String pass = req.getParameter("password");
		int id = Integer.parseInt(req.getParameter("id"));
		int idThread = (int) session.getAttribute("idThread");

		DAOServlet dao;
		try {

			String passSalted = pass + user;
			String hashedSalted = "";

			StringBuffer hexString = sha256(passSalted);
			hashedSalted = hexString.toString();

			dao = DAOServlet.getDAO();
			if (dao.checkUser(user, hashedSalted)) {
				dao.deleteMessage(id);
				logger.info("\"" + user + "\" deleted a message, id:" + id);
				resp.sendRedirect("/forum/thread?id=" + idThread);

			} else {
				logger.warn("\"" + user + "\" wrong password to delete the message at id:" + id);
				req.setAttribute("error", "invalide");
				req.getRequestDispatcher("/WEB-INF/jsp/deletemessage.jsp").forward(req, resp);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
				| NoSuchAlgorithmException e) {
			logger.error(
					"\"" + user + "\" error while trying to delete message id:" + id + ", error: " + e.getMessage());
		}
	}

	private StringBuffer sha256(String passSalted) throws NoSuchAlgorithmException {
		MessageDigest digest;
		digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(passSalted.getBytes(StandardCharsets.UTF_8));

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString;
	}

}
