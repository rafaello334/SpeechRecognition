var mediaRecorder, audioChunks;
$(document).ready(function(){
	var mediaConstraints = {
		    audio: true,
		};
		navigator.getUserMedia(mediaConstraints, onMediaSuccess, onMediaError);

		function onMediaSuccess(stream) {
		    mediaRecorder = new MediaStreamRecorder(stream);
		    mediaRecorder.mimeType = 'audio/wav';
		    mediaRecorder.ondataavailable = function (blob) {
		    	audioChunks.push(blob);
		    };
			mediaRecorder.onstop = function(event) {
		    	sendAudioToServer(audioChunks);
		    }
		}
		function onMediaError(e) {
		    console.error('media error', e);
		}
});


function actionOnRecognizedCommand(command)
{
  switch(command) {
    case "nextMonth":
    	$("#anchorNextMonth")[0].click();
        break;
    case "previouMonth":
    	$("#anchorPreviousMonth")[0].click();
        break;
    case "nextYear":
    	$("#anchorNextYear")[0].click();
        break;
    case "previousYear":
    	$("#anchorPreviousYear")[0].click();
        break;
    default:
    	$("#diagnostic-text").text("I didn't recognize command: ");
    	$("#try-again").text("Click on image to try again");
    	break;
  }
}

function toggleRecording(e) {
    if (e.classList.contains("recording")) {
    	e.classList.remove("recording"); 
    	stopRecording(); 
    } else {
        e.classList.add("recording");
        startRecording();
    }
}

function sendAudioToServer(audioChunks)
{
	var finalBlob = new Blob(audioChunks)
	var data = new FormData();
	data.append('fname', "voice" + new Date().getTime() + ".wav");
	data.append('file', finalBlob);
	$.ajax({
	    method: 'POST',
	    enctype: 'multipart/form-data',
	    url: '/recognizeCommand',
	    data: data,
	    processData: false,
	    contentType: false,
	    success: function(data) {
	    	actionOnRecognizedCommand(data);
	    },    
	    error: function(xhr, ajaxOptions, thrownError) {
	        console.log(xhr.status + " " + thrownError);
	    }
	});
}

function startRecording()
{
	audioChunks = [];
	mediaRecorder.start(5000);
};

function stopRecording()
{
	mediaRecorder.stop();
};
