<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>
<meta charset="utf-8" />
<title>LOGIN</title>

<link rel="stylesheet" href="css/style.css" type="text/css" />
</head>
<body class="ltr">

	<div id="wrapcentre">

		<br style="clear: both;" />

		<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
			cellpadding="0" width="100%">
			<tbody>
				<tr>
					<td class="row1">
						<p class="breadcrumbs">
							<a href="/forum/home">Board index</a>
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<br />

		<form action="#" method="post">

			<table class="tablebg" cellspacing="1" width="100%">
				<tbody>
					<tr>
						<th colspan="2">Sign Up</th>
					</tr>

					<tr>
						<td class="row2">

							<table style="width: 100%;" cellspacing="1" cellpadding="4"
								align="center">
								<tbody>
									<tr>
										<td valign="top"><b class="gensmall">Login :</b></td>
										<td><input class="post" name="username" size="25"
											tabindex="1" type="text" placeholder="Username" required /></td>
									</tr>
									<tr>
										<td valign="top"><b class="gensmall">Mot de passe:</b></td>
										<td><input class="post" name="password" size="25"
											tabindex="2" type="password" placeholder="Password" required/></td>
									</tr>
									
									<c:if test="${ error == 'exist'}">
									<tr>
										<td></td>
										<td style="color:#FF0000">Username already taken!</td>
									</tr>
									</c:if>
									
									<c:if test="${ error == 'emptyfields'}">
									<tr>
										<td></td>
										<td style="color:#FF0000">You can not submit empty fields</td>
									</tr>
									</c:if>
									
								</tbody>
							</table>
						</td>
					</tr>

					<tr>
						<td class="cat" colspan="2" align="center">
							<input name="login" class="btnmain" value="Sign Up" tabindex="5" type="submit" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<table class="tablebg" style="margin-top: 5px;" cellspacing="1"
			cellpadding="0" width="100%">
			<tbody>
				<tr>
					<td class="row1">
						<p class="breadcrumbs"><a href="/forum/home"><u><b>Index du forum</b></u></a></p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
