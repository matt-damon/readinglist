package com.manning.readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class ReadingListController {

	private ReadingListRepository readingListRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository) {
		this.readingListRepository = readingListRepository;
	}
	
//	@RequestMapping(method=RequestMethod.GET, value="/fail")
//	public void fail() {
//	  throw new RuntimeException();
//	}
//
//	@ExceptionHandler(value=RuntimeException.class)	@ResponseStatus(value=HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
//	public String error() {
//	  return "error";
//	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String readersBooks(Reader reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
		}
		return "readingList";//Thymeleaf模板
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addToReadingList(Reader reader, Book book) {
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/?username=" + reader.getUsername();
	}
	
}
