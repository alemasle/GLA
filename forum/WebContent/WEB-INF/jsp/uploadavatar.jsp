<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" xml:lang="en-gb"
	lang="en-gb">
<head>
<meta charset="utf-8" />
<title>Upload Avatar</title>

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

		<form action="#" method="post" enctype="multipart/form-data">

			<table class="tablebg" cellspacing="1" width="100%">
				<tbody>
					<tr>
						<th colspan="2">Upload Avatar</th>
					</tr>

					<tr>
						<td class="row2">

							<table style="width: 100%;" cellspacing="1" cellpadding="4" align="center">
								<tbody>
									<tr>
										<td valign="top"><b class="gensmall"> <c:out value="${user}"/> nouvel avatar:</b></td>
										<td><input class="post" name="avatar" type="file" accept="image/*" required/></td>
									</tr>
									<c:if test="${ error == 'emptyfields'}">
										<tr>
											<td></td>
											<td style="color:#FF0000">You can not submit empty fields</td>
										</tr>
									</c:if>
									<c:if test="${ error == 'convert'}">
										<tr>
											<td></td>
											<td style="color:#FF0000">Your image is invalid or corrupted</td>
										</tr>
									</c:if>
									<c:if test="${ error == 'tooLarge'}">
										<tr>
											<td></td>
											<td style="color:#FF0000">Your image is too big, choose an other one (&lt; 1Mo)</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>

					<tr>
						<td class="cat" colspan="2" align="center">
							<input name="upload" class="btnmain" value="Upload" tabindex="5" type="submit" />
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
