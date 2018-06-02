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

	var bg = document.querySelector('html');
	var newEvent = false;

	
	recognition.onresult = function(event) {
	  var last = event.results.length - 1;
	  var command = event.results[last][0].transcript;
	  switch(command) {
	    case "next month":
	    	$("#anchorNextMonth")[0].click();
	        break;
	    case "previous month":
	    	$("#anchorPreviousMonth")[0].click();
	        break;
	    case "next year":
	    	$("#anchorNextYear")[0].click();
	        break;
	    case "previous year":
	    	$("#anchorPreviousYear")[0].click();
	        break;
	    case "actual":
	    	$("#anchorActualMonth")[0].click();
	        break;
	    default:
	        if(newEvent)
	        {	
	        	command = command.charAt(0).toUpperCase() + command.slice(1);
	        	$('#message-input').val(command);
	        	hideSpeechModal();
	        }
	        else 
	        {
	        	$("#diagnostic-text").text("I didn't recognize command: " + command);
	        	$("#try-again").text("Click on image to try again");
	        }
	    	
	    	newEvent = false;
	    	break;
	  }
	}
	
	recognition.onspeechend = function() {
		recognition.stop();
	}
	
	// Speech modal window show and close
	
	var showSpeechModal = function()
	{
		$("#btnSpeechRecognitionStart").click();
	}
	
	var hideSpeechModal = function()
	{
		$("#btnSpeechClose").click();	
	}


	$('#message-input').click(function() {	
		newEvent = true;
		showSpeechModal();
	});
	
	$('#btnSpeechRecognitionStart').click(function() {	
		recognition.start();
		$("#diagnostic-text").text("Waiting for command...");
	});
	
	$('#img-microphone').click(function() {	
		$("#diagnostic-text").text("Waiting for command...");
    	$("#try-again").text("");
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
