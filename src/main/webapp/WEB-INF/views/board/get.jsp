<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../includes/header.jsp" />

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Board Read Page</h1>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Board Read Page</h6>
		</div>
		<form method="post">
		<div class="card-body">
			<div class="form-group">
				<label for="title">bno</label> 
				<input type="text" class="form-control" placeholder="Enter bno" id="bno" name="bno" value="${board.bno}" readonly>
			</div>
			<div class="form-group">
				<label for="title">title</label> 
				<input type="text" class="form-control" placeholder="Enter title" id="title" name="title" value="${board.title}" readonly>
			</div>
			<div class="form-group">
				<label for="comment">content</label> 
				<textarea rows="10" class="form-control" id="comment" name="content" readonly>${board.content}</textarea>
			</div>
			<div class="form-group">
				<label for="writer">writer</label> 
				<input type="text" class="form-control" placeholder="Enter writer" id="writer" name="writer" value="${board.writer}" readonly>
			</div>
			<c:if test="${not empty board.attachs[0].uuid}">
			<div class="form-group">
				<label for="file">file <br> <span class="btn btn-primary">파일첨부</span></label> 
				<div class="uploadResult my-3">
					<ul class="list-group filenames my-3">
						<c:forEach items="${board.attachs}" var="attach">
						<li class="list-group-item" data-uuid="${attach.uuid}" data-name="${attach.name}" data-path="${attach.path}" data-image="${attach.image}">
						<a href="/download${attach.url}&thumb=off"><i class="far fa-file"></i> ${attach.name}</a> <!-- <i class="far fa-times-circle btn-remove float-right" data-file="name=The+Fablemans.jpg&amp;path=2023%2F04%2F17&amp;uuid=0b3740d6-879f-442e-921d-b92772452392&amp;image=true&amp;thumb=off"></i>--> 
						</li> 					
						</c:forEach>
					</ul>
					<ul class="nav thumbs">
						<c:forEach items="${board.attachs}" var="attach">
							<c:if test="${attach.image}">
								<li class="nav-item m-2" data-uuid="${attach.uuid}">
									<a class="img-thumb" href="">
									<img class="img-thumbnail" src="/display${attach.url}&thumb=on" data-src="${attach.url}&thumb=off"></a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			</c:if>
			<sec:authorize access="isAuthenticated() and principal.username eq #board.writer">
			<a href="modify${cri.fullQueryString}&bno=${board.bno}" class="btn btn-outline-warning">modify</a>
			</sec:authorize>
			<a href="list${cri.fullQueryString}" class="btn btn-secondary">list</a>
			</div>
		</form>
	</div>
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary float-left">Reply</h6>
			<sec:authorize access="isAuthenticated()">
			<button class="btn btn-primary float-right btn-sm" id="btnReg">New Reply</button>
			</sec:authorize>
		</div>
		<div class="card-body">
			<ul class="list-group chat">
			</ul>
			<button class="btn btn-primary btn-block my-4 col-md-8 mx-auto btn-more">더보기</button>
		</div>
	</div>
	<!-- /.container-fluid -->
	<div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-labelledby="replyModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Reply Modal</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                	<div class="form-group">
						<label for="reply">Reply</label> 
						<input type="text" class="form-control" id="reply" placeholder="Enter reply">
					</div>
                	<div class="form-group">
						<label for="replyer">Replyer</label> 
						<input type="text" class="form-control" id="replyer" readonly="readonly">
					</div>
                	<div class="form-group">
						<label for="reply">Reply Date</label> 
						<input type="text" class="form-control" id="replydate">
					</div>
                </div>
                <div class="modal-footer" id="modalFooter">
                    <button class="btn btn-warning" type="button" data-dismiss="modal">Modify</button>
                    <button class="btn btn-danger" type="button" data-dismiss="modal">Remove</button>
                    <button class="btn btn-primary" type="button" data-dismiss="modal">Register</button>
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

<script>
	var cp = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/reply.js"></script>
<script>
	$(function(){
		var csrfHeader = '${_csrf.headerName}';
		var csrfToken = '${_csrf.token}';
		var bno = '${board.bno}';
		var replyer = '';
		<sec:authorize access="isAuthenticated()">
			replyer = '<sec:authentication property="principal.username"/>';
		</sec:authorize>
		$(document).ajaxSend(function(e, xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		})
		
		moment.locale('ko');
		replyService.getList({bno:bno}, function(result){
			var str = "";
			for(var i in result) {
				str += getReplyLiStr(result[i]);
			}
			$(".chat").html(str);
		});
		$("#btnReg").click(function() {
			$("#replyModal").find("input").val("");
			$("#replyer").val(replyer);
			$("#replydate").closest("div").hide();
			$("#modalFooter button").hide();
			$("#modalFooter button").eq(2).show();
			$("#modalFooter button").eq(3).show();
			$("#replyModal").modal("show");
		})
		
		$(".chat").on("click", "li", function() {
			//console.log($(this).data("rno"));
			replyService.get($(this).data("rno"), function(result){
				$("#reply").val(result.reply);
				$("#replyer").val(result.replyer);
				$("#replydate").val(moment(result.replydate).format("LLL:ss")).prop("readonly", true).closest("div").show();
				$("#modalFooter button").show();
				$("#modalFooter button").eq(2).hide();
				if(replyer != result.replyer) {
					$("#modalFooter button").eq(1).hide();
					$("#modalFooter button").eq(0).hide();
				}
				$("#replyModal").modal("show").data("rno", result.rno);
			}) 
		});
		
		// 글작성이벤트
		$("#modalFooter button").eq(2).click(function() {
			var obj = {bno:bno, reply:$("#reply").val(), replyer:$("#replyer").val()}
			replyService.add(obj, function(result) {
				$("#replyModal").find("input").val("");
				$("#replyModal").modal("hide");		
				replyService.get(result, function(data) {
					$(".chat").prepend(getReplyLiStr(data));
				})
			});
		});
		
		// 글삭제이벤트
		$("#modalFooter button").eq(1).click(function() {
			var obj = {rno:$("#replyModal").data("rno"), replyer:$("#replyer").val()}
			replyService.remove(obj, function(result){
				console.log(result);
				$("#replyModal").modal("hide");
				$(".chat li").each(function() {
					if($(this).data("rno") == obj.rno) {
						$(this).remove();
					}
				})
			})
		});
		
		// 글수정이벤트
		$("#modalFooter button").eq(0).click(function() {
			var obj = {rno:$("#replyModal").data("rno"), reply:$("#reply").val(), replyer:$("#replyer").val()}
			replyService.modify(obj, function(result){
				console.log(result);
				$("#replyModal").modal("hide");
				$(".chat li").each(function() {
					if($(this).data("rno") == obj.rno) {
						var $this = $(this);
						replyService.get($this.data("rno"), function(r){
							$this.replaceWith(getReplyLiStr(r));
						})
					}
				})
			})
		});
		
		$(".btn-more").click(function() {
			var rno = $(".chat li:last").data("rno");
			console.log(rno);
			replyService.getList({bno:bno, rno:rno}, function(result){
				console.log(result);
				if(!result.length) {
					$(".btn-more").prop("disabled", true);
					return;
				}
				var str = "";
				for(var i in result) {
					str += getReplyLiStr(result[i]);
				}
				$(".chat").append(str);
			});
		})
		
		function getReplyLiStr(obj){
			return `<li class="list-group-item" data-rno="\${obj.rno}">
						<div class="header">
							<strong class="primary-font">\${obj.replyer}</strong>
							<small class="float-right text-muted">\${moment(obj.replydate).fromNow()}</small>
						</div>
						<p>\${obj.reply}</p>
					</li>`;
		}
/* 		replyService.getList({bno:bno, rno:21}, function(result){
			console.log(result);
		}) */
/* 		replyService.modify({rno:160, reply:'수정된 댓글내용2'}, function(result){
			console.log(result);
		}) */
		 ClassicEditor.create($('#comment').get(0), {
	    	  toolbar :[]
	      }).then(function(editor) {
	    	  editor.enableReadOnlyMode('lock');
	      })
	})
</script>
<script>
$(function() {
	$("form button").click(function() {
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
		$("form").append(str).submit();
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
				str += `<li class="list-group-item" data-uuid="\${obj.uuid}" data-name="\${obj.name}" data-path="\${obj.path}" data-image="\${obj.image}">
						<a href="/download?\${obj.url}"><i class="far fa-file"></i>`;
				str += obj.name + `</a> <i class="far fa-times-circle btn-remove float-right" data-file="\${obj.url}"></i> </li>`;
			if(obj.image) {
				imgStr += `<li class="nav-item m-2" data-uuid="\${obj.uuid}"><a class="img-thumb" href="">
				<img class="img-thumbnail" src="/display\${obj.url}&thumb=on" data-src="\${obj.url}"></a></li>`;
			}
		}
		console.log(str);
		$(".uploadResult .filenames").append(str);
		$(".uploadResult .thumbs").append(imgStr);
/* 		$(":file").get(0).reset(); */
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
})
</script>
<jsp:include page="../includes/footer.jsp" />