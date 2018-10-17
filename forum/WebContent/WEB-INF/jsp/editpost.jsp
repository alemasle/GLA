<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>

<title>Edit Message</title>

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
						<th colspan="2">Edit Message</th>
					</tr>

					<tr>
						<td class="row2">
							<table style="width: 100%;" cellspacing="1" cellpadding="4"
								align="center">
								<tbody>
									<tr>
										<td valign="top"><b class="gensmall">Message:</b></td>
										<td><textarea name="texte" rows="10" cols="100" maxlength="5000" placeholder="Votre message...(5000 caractères max)" ><c:out value="${txt}" /></textarea></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>

					<tr>
						<td class="cat" colspan="2" align="center">
							<input name="message" class="btnmain" value="Submit" tabindex="5" type="submit" />
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
