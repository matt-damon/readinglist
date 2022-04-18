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
    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository,
                                 AmazonProperties amazonProperties) {
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
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


    @RequestMapping(method = RequestMethod.GET)
    public Object readersBooks(Reader reader, Model model) {
        List<Book> readingList = readingListRepository.findByReader(reader.getUsername());
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonId", amazonProperties.getAssociateId());
        }
        return "readingList";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String addToReadingList(Reader reader, Book book) {
        book.setReader(reader.getUsername());
        readingListRepository.save(book);
        return "redirect:/?username=" + reader.getUsername();
    }

}
