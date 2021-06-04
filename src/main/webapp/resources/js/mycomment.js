/**
 *  mycomment.js: 댓글/답글 데이터 처리용 Ajax Closure Module
 */

var myCommentClsr = (function(){
	//브라우저의 상세 페이지에서 Javascript에 의해 호출 시 전달받는 매개변수 설명
    //bnoAndPage: 서버로 전달할 값,
    //callback: 서버 처리 성공 시 브라우저에 의해 처리되는 함수
    //error: 서버 처리 실패 시 브라우저에 의해 처리되는 함수

    //댓글 목록(페이징) - ajax() 함수 사용
	function getCmtList(pagingParams, callback, error){
		var bno = pagingParams.bno;
		var page = pagingParams.page||1;	//page라는 변수에 값이 없으면 1으로
		
		console.log("getCmtList()가 전달받은 bno: "+bno);
		console.log("getCmtList()가 전달받은 page: "+page);
		console.log("getCmtList()함수의 ajax 처리 시작.....");
		
		//$.ajax() 함수는, 자바스크립트 객체를 매개값으로 받아 처리
		$.ajax({
			type: 'get',	//전송유형: get 방식
			url: "/ex00/replies/pages/"+bno+"/"+page,	//컨트롤러 메소드 호출 URL
			dataType: 'json',	//서버로부터 받는 값들이 JSON 형식임(서버의  produces 속성에 대응)
			//서버처리 성공 시 실행 함수, XHR (XML Http Request)
            //result 매개변수를 통해 서버로부터 전달받은 데이터가 callback 함수에 의해 처리됨
			success: function(replyPagingCreator, status, xhr){
				if(callback){	//callback 매개변수에 함수가 있으면 ,참
					callback(replyPagingCreator);
				}
			},
			//서버처리 실패 시, 매개변수로 전달된 함수 실행, XHR (XML Http Request)
			error: function(xhr, status, er){
				if(error){	//error 매개변수에 함수가 있으면 ,참
					error(er);
				}
			}
		});
	}
	
	
    //댓글 목록(페이징) - getJSON() 함수 사용
/*    function getCmtList(bnoAndPage, callback, error) {

        var bno = bnoAndPage.bno;
        var page = bnoAndPage.page || 1;

        console.log("getCmtList()가 전달받은 bno: " + bno) ;
        console.log("getCmtList()가 전달받은 page: " + page) ;
        console.log("getCmtList()함수의 ajax 처리 시작......");
	
        //$.getJSON() 함수는, 컨트롤러 메서드에 get 방식으로 요청하고, 결과를  JSON 객체를 받음 
        //[형식] $.getJSON(url, [data,서버로 전달할 데이터], callback-function)
        //서버에서 오류발생 시, 처리할 함수가 지정되지 못하므로, fail() 함수로 별도로 지정해야 함

        $.getJSON( "/ex00/replies/" + bno + "/pages/" + page ,
                   function(myCmtPagingCreator) {
                       if (callback) {
                           callback(myCmtPagingCreator);//data는 댓글 전체 목록
				       }
                    } //callback 처리함수 END
        ).fail(function(xhr, status, err) { //jQuery Ajax Chaining 메서드: fail 메서드 사용(ajax의 error를 대체)
                   if (error) {
                       error();
                   }
               }
        );
    }//getCmtList END
	*/
	
	//댓글 등록
	function registerCmt(comment, callback, error){
		var bno = comment.bno;
		
		console.log("registerCmt() 전달받은 bno: "+bno);
		console.log("registerCmt() 함수의 comment 등록 ajax 처리 시작..........");
		
		//data: 브라우저가 서버에게 보내는 데이터
        //contentType: 서버에게 보내는 데이터의 MIME타입 설정, 컨트롤러의 consumes 속성에 대응
		$.ajax({
			type: "post",
			url: "/ex00/replies/"+bno+"/new",
			data: JSON.stringify(comment),
			contentType: "application/json; charset=utf-8",
			//dataType: "text",
			success: function(result, status, xhr){
				if(callback){
					callback(result);
				}
			},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	//답글 등록
	function registerReply(reply, callback, error){
		var bno = reply.bno;
		var prno = reply.prno;
		
		console.log("registerReply() 전달받은 bno: " + bno);
        console.log("registerReply() 전달받은 prno: " + prno);
        console.log("registerReply()함수의 댓글에 대한 답글 등록 ajax 처리 시작......");

		//data: 브라우저가 서버에게 보내는 데이터
        //contentType: 서버에게 보내는 데이터의 MIME타입 설정, 컨트롤러의 consumes 속성에 대응
		
		$.ajax({
			type: "post",
			url: "/ex00/replies/"+bno+"/"+prno+"/new",
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(result, status, xhr){
				if(callback){
					callback(result);
				}
			},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	//JQuery Ajax의 get() 메소드 사용: url 주소로 GET 방식으로 요청, 객체로 받음
    //get( url [, data] [, success(data, textStatus, jqXHR)] [, dataType] )
    //댓글 조회
	function getCmtReply(bnoAndRno, callback, error){
		var bno = bnoAndRno.bno;
		var rno = bnoAndRno.rno;
		
		console.log("getCmtReply() 전달받은 bno: " + bno);
        console.log("getCmtReply() 전달받은 cno: " + rno);
        console.log("getCmtReply() 함수의 특정 댓글에 조회 ajax 처리 시작......");

		$.get("/ex00/replies/"+bno+"/"+rno+".json", 
			function(result){
				if(callback){
					callback(result);
				}
			}
		).fail(function(xhr, status, err){
			if(error){
				error(err);
			}
		});
	}
	
	//댓글 수정
	function modifyCmtReply(comment, callback, error){
		var bno = comment.bno;
		var rno = comment.rno;
		
		console.log("modifyCmtReply() 전달받은 bno: " + bno);
        console.log("modifyCmtReply() 전달받은 rno: " + rno);
        console.log("modifyCmtReply() 함수의 특정 댓글 수정 ajax 처리 시작......");

		$.ajax({
			type: "put",
			url: "/ex00/replies/"+bno+"/"+rno,
			data: JSON.stringify(comment),
			contentType: "application/json; charset=utf-8",
			dataType: "text",
			success: function(modifyResult, status, xhr){
				if(callback){
					callback(modifyResult);
				}
			},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	//댓글 삭제(cdelFlg만 1로 수정) 
	function setCmtReplyDeleted(comment, callback, error){
		var bno = comment.bno;
        var rno = comment.rno;
        var rwriter = comment.rwriter
	
        console.log("modifyCmtReply() 전달받은 bno: " + bno);
        console.log("modifyCmtReply() 전달받은 rno: " + rno);
        console.log("modifyCmtReply() 함수의 댓글-답글 삭제1 ajax 처리 시작......");

		$.ajax({ 
			type: "patch",
            url : "/ex00/replies/" + bno + "/" + rno,
            data: JSON.stringify(comment),
            contentType: "application/json; charset=utf-8",
            dataType : "text",  // 서버로부터 전달받는 데이터 유형을 설정(xml, text, html, json 등)
            success: function(modifyResult, status, xhr) {
				if (callback) {
                     callback(modifyResult);
                }
            },
            error: function(xhr, status, er) {
                if (error) {
                     error(er);
            	}
            }
         });
	}
	
	//댓글 삭제(실제 삭제)
	function removeCmtReply(comment, callback, error){
		var bno = comment.bno;
        var rno = comment.rno;
        var rwriter = comment.rwriter
	
        console.log("removeCmtReply() 전달받은 bno: " + bno);
        console.log("removeCmtReply() 전달받은 rno: " + rno);
        console.log("removeCmtReply() 전달받은 rwrier: " + rwriter);
        console.log("removeCmtReply() 함수의 댓글 삭제 ajax 처리 시작.......");

		$.ajax({
			type: "delete",
			url: "/ex00/replies/"+bno+"/"+rno,
			data: JSON.stringify({bno: bno, rno: rno, rwriter: rwriter}),
			contentType: "application/json; charset=utf-8",
			success: function(removeResult, status, xhr){
				if(callback){
					callback(removeResult);
				}
			},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	//날자시간 표시형식 설정(서버와 상관 없음)
    //JSON 날짜시간을 그대로 표시하면 1594169682000 이렇게 표시됩니다.
    //일반적인 날짜 시간 표시 형식으로 표시, 
	function showDatetime(datetimeValue){
		var dateObj = new Date(datetimeValue);//전달된 수정 날짜시간 값 저장

        var str ="";

        var yyyy = dateObj.getFullYear(); //YYYY
        var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
        var dd = dateObj.getDate();

        var hh = dateObj.getHours();
        var mi = dateObj.getMinutes();
        var ss = dateObj.getSeconds();

        str= [yyyy, '/', 
             (mm > 9 ? '' : '0') + mm, '/',
             (dd > 9 ? '' : '0') + dd, ' ',
             (hh > 9 ? '' : '0') + hh, ':', 
             (mi > 9 ? '' : '0') + mi,	':', 
             (ss > 9 ? '' : '0') + ss].join(''); //배열값으로 하나의 문자열로 합침

        return str ;
	}
	
	return { 
        getCmtList : getCmtList,   //댓글 목록 
        registerCmt : registerCmt,   //댓글 등록 
        registerReply : registerReply,   //답글 등록
        getCmtReply : getCmtReply,   //댓글-답글 조회
        modifyCmtReply : modifyCmtReply,   //댓글-답글 수정
        setCmtReplyDeleted : setCmtReplyDeleted,    //댓글삭-답글 삭제(cdelFlg=1 수정)
        removeCmtReply : removeCmtReply,    //댓글-답글 삭제(실제삭제)
        showDatetime : showDatetime  //날짜시간 표시형식 지정
//        showLastModTime : showLastModTime 
    };
	
	
})();



/*

    //날짜시간 표시형식 설정: 날짜시간 형식 지정(서버와 상관 없음)
    //JSON 날짜시간을 그대로 표시하면 1594169682000 이렇게 표시됩니다.
    //오늘 지금시간을 기준으로 하루를 넘어가면, 날짜가 표시, 하루가 지나지 않았으면, 시간으로 표시됩니다 
	//마지막 수정된 시간표시, 날짜시간 표시 함수
    function displayTime(timeValue) {

        var todayNow = new Date(); //현재 날짜시간 저장
        //gap에는 하나의 정수값이 저장됩니다.(JSON날짜시간이 정수값 
        //현재날짜시간과 댓글 수정된 날짜시간차이
        var gap = todayNow.getTime() - timeValue; //현재날짜시간과 댓글 수정된 날짜시간차이
        var dateObj = new Date(timeValue);//전달된 댓글의 수정날짜시간값 저장
        //하루를 밀리초 단위까지 고려한 정수값으로 변환하여 비교
        if (gap < (1000 * 60 * 60 * 24)) {
            //시:분:초로 표시
            var hh = dateObj.getHours();
            var mi = dateObj.getMinutes();
            var ss = dateObj.getSeconds();
            //배열로 구성하여 이를 하나의 문자열로 합쳐서 반환
            return [ (hh > 9 ? '' : '0') + hh, ':',
                     (mi > 9 ? '' : '0') + mi, ':',
                     (ss > 9 ? '' : '0') + ss ].join(''); //배열값으로 하나의 문자열로 합침(13:16:41 문자열)

        } else {//차이가 하루보다 보다 크거나 같으면  년/얼/일 형식으로 표시
            var yy = dateObj.getFullYear(); //YYYY
            var mm = dateObj.getMonth() + 1; // getMonth(): 0~11
            var dd = dateObj.getDate();

            return [ yy, '/', 
                    (mm > 9 ? '' : '0') + mm, '/',
                    (dd > 9 ? '' : '0') + dd ].join('');  //2021/01/27 문자열
        }
    }

    //날자시간 표시형식 설정: 날짜시간 형식 지정(서버와 상관 없음)
    //하루 기준으로 날짜나 날짜시간으로 표시되는 구분없이 항상 날짜시간으로 표시할 경우, 
    function showLastModTime(timeValue) {
    //JSON 날짜시간을 그대로 표시하면 1594169682000 이렇게 표시됩니다.
        var dateObj = new Date(timeValue);//전달된 수정날짜시간값 저장

        var str ="";

        var yy = dateObj.getFullYear(); //YYYY
        var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
        var dd = dateObj.getDate();

        var hh = dateObj.getHours();
        var mi = dateObj.getMinutes();
        var ss = dateObj.getSeconds();

        str= [yy, '/', 
             (mm > 9 ? '' : '0') + mm, '/',
             (dd > 9 ? '' : '0') + dd, ' ',
             (hh > 9 ? '' : '0') + hh, ':', 
             (mi > 9 ? '' : '0') + mi,	':', 
             (ss > 9 ? '' : '0') + ss].join(''); //배열값으로 하나의 문자열로 합침

        return str ;
	}
*/	