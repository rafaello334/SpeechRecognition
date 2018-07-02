package pl.speechrecognition.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import pl.speechrecognition.SpeechRecognitionApplication;
import pl.speechrecognition.model.Event;

@Component
public class SimplyTools {

	private Calendar calendar = new GregorianCalendar();

	public List<Event> findEventsForDay(List<Event> eventsList, int day, int month, int year) {
		List<Event> dayEvents = new ArrayList<>();
		for (Event event : eventsList) {
			calendar.setTime(event.getDate());
			if (day == calendar.get(Calendar.DAY_OF_MONTH) && month == calendar.get(Calendar.MONTH)
					&& year == calendar.get(Calendar.YEAR))
				dayEvents.add(event);
		}
		return dayEvents;
	}

	public File saveFile(MultipartFile file, String fileName) throws IOException {
		String path = "C:/speech/" + fileName;
		File convFile = new File(path);
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		SpeechRecognitionApplication.logger.info("Sound saved: " + path);
		return convFile;
	}

	public String convertTo16khzWav(String fileName) throws IOException, InterruptedException {
		String outputFile = "out" + fileName;
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
				"cd \"C:\\speech\" && \"C:\\Program Files\\ffmpeg\\bin\\ffmpeg\" -i " + fileName + " -acodec pcm_s16le -ac 1 -ar 16000 " + outputFile);
		Process process = builder.start();
        process.waitFor();
		SpeechRecognitionApplication.logger.info("File converted to recognizable format: " + fileName);
		return outputFile;
	}

}
