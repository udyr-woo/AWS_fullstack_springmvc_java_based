<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<jsp:include page="../includes/header.jsp" />
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Tables</h1>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Board Register</h6>
		</div>
		<form method="post" id="boardform">
		<div class="card-body">
			<div class="form-group">
				<label for="title">title</label> 
				<input type="text" class="form-control" placeholder="Enter title" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="comment">content</label> 
				<textarea rows="10" class="form-control" id="comment" name="content"></textarea>
			</div>
			<div class="form-group">
				<label for="writer">writer</label> 
				<input type="text" class="form-control" id="writer"  value='<sec:authentication property="principal.member.userName"/>' readonly="readonly">
				<input type="hidden" class="form-control" name="writer" value='<sec:authentication property="principal.username"/>' readonly="readonly">
			</div>
			<div class="form-group">
				<label for="file">file <br> <span class="btn btn-primary">파일첨부</span></label> 
				<input type="file" class="form-control d-none" id="file" name="file" multiple="multiple">
				<div class="uploadResult my-3">
					<ul class="list-group filenames my-3"></ul>
					<ul class="nav thumbs"></ul>
				</div>
			</div>
			<button type="submit" class="btn btn-primary" id="btnReg">Submit</button>
			<input type="hidden" name="pageNum" value="1">
			<input type="hidden" name="amount" value="${cri.amount}">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			</div>
		</form>
	</div>
	</div>
	<!-- /.container-fluid -->
<script>
var csrfHeader = '${_csrf.headerName}';
var csrfToken = '${_csrf.token}';
	  let editor;
      ClassicEditor.create($('#comment').get(0), {
    	  ckfinder : {
    		  uploadUrl : '/ckImage.json'
    	  },
    	  headers : {
    		  csrfHeader : csrfToken
    	  }
      })
      .then(function(a) {
    	  editor = a;
      });
</script>
<script>
$(function() {

	
	$("#btnReg").click(function() {
		event.preventDefault();
		var str = '';
		$(".filenames li").each(function(i) {
			console.log(i, this);
			str += `
			<input type="hidden" name="attachs[\${i}].uuid" value="\${$(this).data('uuid')}">
			<input type="hidden" name="attachs[\${i}].name" value="\${$(this).data('name')}">
			<input type="hidden" name="attachs[\${i}].path" value="\${$(this).data('path')}">
			<input type="hidden" name="attachs[\${i}].image" value="\${$(this).data('image')}">
			`;
		})
		console.log(str);
		$("#boardform").append(str).submit();
	})
	function checkExtension(files) {
		const MAX_SIZE = 5 * 1024 * 1024;
		const EXCLUDE_EXT = new RegExp("(.*?\.(js|jsp|asp|php))");
		
		for(let i in files) {
			if(files[i].size >= MAX_SIZE || EXCLUDE_EXT.test(files[i].name)) {
				return false;	
			}
		}	
		return true;
	}
	$("#file").change(function() {
		event.preventDefault();
		let files = $("#file").get(0).files;
		console.log(files);
		if(!checkExtension(files)) {
			alert("조건 불일치");
			return false;
		}
		
		let formData = new FormData();
		
		for(let i in files) {
			formData.append("files", files[i])
		}
		
		$.ajax({
			url: "/uploadAjax",
			processData : false,
			contentType : false,
			beforeSend : function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			data : formData,
			method : 'POST',
			success : function(data) {
				console.log(data);
				$("form").get(0).reset();
				showUploadedFile(data);
			}
		})
	})
	function showUploadedFile(uploadResultArr) {
		var str = "";
		var imgStr = "";
		for(var i in uploadResultArr) {
				let obj = uploadResultArr[i];
				console.log(obj);
				str += `<li class="list-group-item" data-uuid="\${obj.uuid}" data-name="\${obj.name}" data-path="\${obj.path}" data-image="\${obj.image}">
						<a href="/download\${obj.url}"><i class="far fa-file"></i>`;
				str += obj.name + `</a> <i class="far fa-times-circle btn-remove float-right" data-file="\${obj.url}"></i> </li>`;
			if(obj.image) {
				imgStr += `<li class="nav-item m-2" data-uuid="\${obj.uuid}"><a class="img-thumb" href="">
				<img class="img-thumbnail" src="/display\${obj.url}&thumb=on" data-src="\${obj.url}"></a></li>`;
				console.log(imgStr);
			}
		}
		console.log(str);
		$(".uploadResult .filenames").append(str);
		$(".uploadResult .thumbs").append(imgStr);
/* 		$("form").get(0).reset(); */
	}
	
	$(".uploadResult ul").on("click", ".btn-remove", function() {
		var file = $(this).data("file");		
		$.ajax({
			url : "/deleteFile" + file,
			success : function(data) {
				$('[data-uuid="' + data + '"]').remove();
			}
		}) 
	});
	$(".uploadResult ul").on("click", ".img-thumb", function() {
		event.preventDefault();
		var param = $(this).find("img").data("src");
		$("#showImageModal").find("img").attr("src", "/display" + param).end().modal("show");
	});
	
 	$('.filenames').sortable({
		change : function() {
			console.log(this);
		}
	}).css({cursor: "move"}); 
})
</script>
	<jsp:include page="../includes/footer.jsp" />