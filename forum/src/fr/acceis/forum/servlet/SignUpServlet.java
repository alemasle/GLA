package fr.acceis.forum.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.acceis.forum.entity.Utilisateur;

public class SignUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("username");
		String pass = req.getParameter("password");

		if ("".compareTo(user) == 0 || "".compareTo(pass) == 0) {
			System.out.println("Fields empty");
			req.setAttribute("error", "emptyfields");
			req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
		} else if (pass.length() < 8) {
			System.out.println("Signup password too short: " + pass.length());
			req.setAttribute("error", "minlength");
			req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
		} else {

			char[] interdit = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]".toCharArray();
			ArrayList<Character> unwantedChars = new ArrayList<>();
			for (char ch : interdit) {
				unwantedChars.add(ch);
			}
			boolean formatOk = true;
			for (char c : user.toCharArray()) {
				if (unwantedChars.contains(c))
					formatOk = false;
			}

			if (!formatOk) {
				System.out.println("Fail to create user: invalid chars " + user);
				req.setAttribute("error", "invalidChars");
				req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
			}

			else {
				DAOServlet dao;
				try {

					String passSalted = pass + user;
					String hashedSalted = "";

					StringBuffer hexString = sha256(passSalted);
					hashedSalted = hexString.toString();

					dao = DAOServlet.getDAO();

					if ("default".compareTo(user) == 0) {
						System.out.println("Fail to create user!");
						req.setAttribute("error", "exist");
						req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
					}

					else if (dao.addUser(user, hashedSalted)) {
						Utilisateur u = dao.getUser(user);
						HttpSession session = req.getSession();
						session.setAttribute("sess", true);
						session.setAttribute("user", user);
						session.setAttribute("utilisateur", u);
						resp.sendRedirect("/forum/home");
						System.out.println("--> " + u.getRole().getRole() + " : " + user + " -- signup success ("
								+ req.getRemoteAddr() + ")");
					}

					else {
						System.out.println("Fail to create user!");
						req.setAttribute("error", "exist");
						req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
					}
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
						| NoSuchAlgorithmException | SQLException e) {
					e.printStackTrace();
				}
			}
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
