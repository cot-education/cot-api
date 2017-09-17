package org.collegeopentextbooks.api;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.collegeopentextbooks.api.model.Author;
import org.collegeopentextbooks.api.model.Editor;
import org.collegeopentextbooks.api.model.Organization;
import org.collegeopentextbooks.api.model.Repository;
import org.collegeopentextbooks.api.model.Resource;
import org.collegeopentextbooks.api.model.Review;
import org.collegeopentextbooks.api.model.ReviewType;
import org.collegeopentextbooks.api.model.Reviewer;
import org.collegeopentextbooks.api.model.Tag;
import org.collegeopentextbooks.api.service.AuthorService;
import org.collegeopentextbooks.api.service.EditorService;
import org.collegeopentextbooks.api.service.OrganizationService;
import org.collegeopentextbooks.api.service.RepositoryService;
import org.collegeopentextbooks.api.service.ResourceService;
import org.collegeopentextbooks.api.service.ReviewService;
import org.collegeopentextbooks.api.service.ReviewerService;
import org.collegeopentextbooks.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan("org.collegeopentextbooks.api")
@PropertySource("classpath:application.properties")
public class ParserApplication {
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ParserApplication.class);
        try {
	        ParserApplication parser = context.getBean(ParserApplication.class);
	        parser.start();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
    }

	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	
	@Value("${spring.datasource.username}")
	private String datasourceUsername;
	
	@Value("${spring.datasource.password}")
	private String datasourcePassword;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setUrl(datasourceUrl);
		datasource.setUsername(datasourceUsername);
		datasource.setPassword(datasourcePassword);
		return datasource;
	}
	
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private EditorService editorService;
    
    @Autowired
    private OrganizationService organizationService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    private ReviewerService reviewerService;
    
    public void start() {
        // TODO Put parser code here
    	Organization organization = new Organization();
    	organization.setName("Muggle Myopia");
    	organization.setUrl("http://www.amazon.com");
    	organization.setLogoUrl("http://www.amazon.com/muggles.png");
    	organizationService.save(organization);
    	
    	List<Author> authors = new ArrayList<Author>();
    	Author author = new Author();
    	author.setName("Henry Higgins");
    	authors.add(author);
    	authorService.save(author);
    	
    	List<Editor> editors = new ArrayList<Editor>();
    	Editor editor = new Editor();
    	editor.setName("Alfred Hitchcock");
    	editors.add(editor);
    	editorService.save(editor);
    	
    	Repository repository = new Repository();
    	repository.setName("College Open Textbooks");
    	repository.setOrganization(organization);
    	repository.setUrl("http://www.collegeopentextbooks.org");
    	repositoryService.save(repository);
    	
    	Resource resource = new Resource();
    	resource.setRepository(repository);
    	resource.setTitle("Muggles in the Wild");
    	resource.setUrl("http://www.google.com/books/Muggles-in-the-Wild");
    	resource.setExternalReviewUrl("http://www.amazon.com/book-review");
    	resource.setLicense("CC");
    	resource.setAncillariesUrl("http://www.collegeopentextbooks.org/ancillaries");
    	resourceService.save(resource);
    	
    	for(Author currentAuthor: authors) {
    		resourceService.addAuthorToResource(resource, currentAuthor);
    	}
    	for(Editor currentEditor: editors) {
    		resourceService.addEditorToResource(resource, currentEditor);
    	}
    	resource.setAuthors(authors);
    	resource.setEditors(editors);
    	
    	Tag tag = tagService.getByName("Literature");
    	resourceService.addTagToResource(resource, tag);
    	
    	Reviewer reviewer = new Reviewer();
    	reviewer.setName("Mike Pouraryan");
    	reviewer.setTitle("Adjunct Faculty");
    	reviewer.setOrganization(organization);
    	reviewer.setBiography("I have had over 18 years experience in Operations, Finance & Adminstration for Small to Medium Size Businesses, start-ups and publicly held companies. I have also served as an Adjunct Professor for a number of years with a special focus on Management & Public Policy. I also serve as principal moderator for the \"Weekly Outsider\".");
    	reviewerService.save(reviewer);
    	
    	Review review = new Review();
    	review.setResource(resource);
    	review.setReviewer(reviewer);
    	review.setReviewType(ReviewType.CONTENT);
    	review.setScore(4.8);
    	review.setChartUrl("http://www.collegeopentextbooks.org/images/Reviews/business ethics.png");
    	review.setComments("I recommend this textbook as primary textbook for both associate and bachelor level programs. It began with some hypothetical text cases which were tough but it laid out a critical decision making process. The dialogues on Ethical Decision Making and Corporate Social Governance are more necessary than ever. I was looking to bring something into the classroom that focused on the here and this text provides that. I think introducing essential definitions a little earlier could be helpful.");
    	reviewService.save(review);
    }
    
	
}
