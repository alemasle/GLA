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

import fr.acceis.forum.entity.Utilisateur;

public class LoginServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(LoginServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("username");
		String pass = req.getParameter("password");

		if ("".compareTo(user) == 0 || "".compareTo(pass) == 0) {
			logger.warn("visitor " + req.getRemoteAddr() + " tried to log in with empty fields");
			req.setAttribute("error", "emptyfields");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		} else {

			DAOServlet dao;
			try {
				String passSalted = pass + user;
				String hashedSalted = "";

				StringBuffer hexString = sha256(passSalted);
				hashedSalted = hexString.toString();

				dao = DAOServlet.getDAO();
				if (dao.checkUser(user, hashedSalted)) { // Eviter d'avoir une image
															// de profil ecrasant celle
															// par defaut

					Utilisateur u = dao.getUser(user);
					HttpSession session = req.getSession();
					session.setAttribute("sess", true);
					session.setAttribute("user", user);
					session.setAttribute("utilisateur", u);

					String path = (String) session.getAttribute("accessWanted");
					logger.info("The " + u.getRole().getRole() + " \"" + user + "\" has connected. ip: "
							+ req.getRemoteAddr());
					resp.sendRedirect(path);

				} else {
					req.setAttribute("error", "invite");
					req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
					logger.warn(
							"visitor " + req.getRemoteAddr() + " failed to connect with username: \"" + user + "\"");
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
					| NoSuchAlgorithmException e) {
				logger.error(
						"error while visitor " + req.getRemoteAddr() + " tried to login, error: " + e.getMessage());
			}

		}
	}

	/**
	 * 
	 * @param passSalted The password to hash
	 * @return The StringBuffer with the password hashed
	 * @throws NoSuchAlgorithmException
	 */
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
