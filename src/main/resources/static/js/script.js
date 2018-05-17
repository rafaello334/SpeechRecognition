$(document).ready(function(){
	
	// Speech recognition - configuration
	
	var SpeechRecognition = SpeechRecognition || webkitSpeechRecognition
	var SpeechGrammarList = SpeechGrammarList || webkitSpeechGrammarList
	var SpeechRecognitionEvent = SpeechRecognitionEvent || webkitSpeechRecognitionEvent
	var commands = [ 'next' , 'previous' , 'actual'];
	var grammar = '#JSGF V1.0; grammar commands; public <commands> = ' + commands.join(' | ') + ' ;'

	var recognition = new SpeechRecognition();
	var speechRecognitionList = new SpeechGrammarList();
	speechRecognitionList.addFromString(grammar, 1);
	recognition.grammars = speechRecognitionList;
	recognition.lang = 'en-US';
	recognition.interimResults = false;
	recognition.maxAlternatives = 1;

	
	var diagnostic = $("#diagnostic-message");
	var bg = document.querySelector('html');
	var newEvent = false;

	
	recognition.onresult = function(event) {
	  var last = event.results.length - 1;
	  var command = event.results[last][0].transcript;
	  switch(command) {
	    case "next":
	    	$("#anchorNextMonth")[0].click();
	        break;
	    case "previous":
	    	$("#anchorPreviousMonth")[0].click();
	        break;
	    case "actual":
	    	$("#anchorActualMonth")[0].click();
	        break;
	    default:
	        if(newEvent)
	        	$('#message-input').val(command);
	        else 
	        	console.log("Command not recognized");
	    	newEvent = false;
	    	break;
	  }
	}
	
	recognition.onspeechend = function() {
	  hideSpeechModal();
	}

	recognition.onnomatch = function(event) {
	  diagnostic.text = "I didn't recognise that command.";
	}

	recognition.onerror = function(event) {
	  diagnostic.text = 'Error occurred in recognition: ' + event.error;
	}
	
	
	// Speech modal window show and close
	
	var showSpeechModal = function()
	{
		$("#btnSpeechRecognitionStart").click();
	}
	
	var hideSpeechModal = function()
	{
		$("#btnSpeechClose").click();
		recognition.stop();
	}
	
		
	// Invoke speech recognition
	
	$('#message-input').click(function() {	
		newEvent = true;
		showSpeechModal();
	});
	
	$('#btnSpeechRecognitionStart').click(function() {	
		recognition.start();
	});
	
	
	
	// Other functions	
	
	$('.day-component').click(function() {
		var yearAndMonth = $('.year-month').find('span').text();
	    var dayNumber = $(this).find('.day-name').text();
	    var outputDate = yearAndMonth + " - " + dayNumber;
	    outputDate = outputDate.replace(/\s/g, '');
	    $("#date-picker").val(outputDate);
	});
});
