package org.collegeopentextbooks.api.controller;

import java.util.List;

import org.collegeopentextbooks.api.model.Book;
import org.collegeopentextbooks.api.model.BookReview;
import org.collegeopentextbooks.api.model.ReviewType;
import org.collegeopentextbooks.api.model.Tag;
import org.collegeopentextbooks.api.service.BookService;
import org.collegeopentextbooks.api.service.ReviewService;
import org.collegeopentextbooks.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping(method=RequestMethod.GET, value="tags", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Tag> getAllTags() {
        return tagService.getAll();
    }
	
	@RequestMapping(method=RequestMethod.GET, value="tag/{tagId}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Book> getBooksByTag(Long tagId) {
        return bookService.getBooksByTag(tagId);
    }
	
	@RequestMapping(method=RequestMethod.GET, value="book/{bookId}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Book getBook(Long bookId) {
        return bookService.getBook(bookId);
    }
	
	@RequestMapping(method=RequestMethod.GET, value="reviews/{bookId}/{reviewType}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<BookReview> getReview(Long bookId, ReviewType reviewType) {
        return reviewService.getReviews(bookId, reviewType);
    }
	
	@RequestMapping(method=RequestMethod.GET, value="review/{reviewId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	BookReview getReview(Long reviewId) {
		return reviewService.getReview(reviewId);
	}
	
}