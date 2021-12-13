package lk.ubiquitouslibrary.util;

import lk.ubiquitouslibrary.entity.BookScrape;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Component
public class Scraper{

    boolean running;

    public List<BookScrape> scrape(){

        // Add the books to this list
        List<BookScrape> books = new ArrayList<>();

        running=true;

        log.info("Starting Scraper");

        try {
            /**
             * Here we create a document object,
             * The we use JSoup to fetch the website.
             */
            System.setProperty("webdriver.gecko.driver","./geckodriver.exe");
            WebDriver driver = new FirefoxDriver();
            driver.get("https://lk1lib.org/popular.php/?language=Java");

            Document doc = Jsoup.parse(driver.getPageSource());

            driver.quit();

            /**
             * With the document fetched,
             * we use JSoup???s title() method to fetch the title
             */
            System.out.printf("\nZLibrary Home: %s\n\n", doc.title());


            // Get the list of repositories
            Elements repositories = doc.getElementsByClass("brick checkBookDownloaded");

            int count = 0;
            /**
             * For each repository, extract the following information:
             * 1. Book URL - String
             * 2. Image URL - String
             * 3. Book Title - String
             * 4. Authors (Some books may have more than one author) - String
             * 5. bookDescription - String
             * 6. bookCategories - String[]
             * 7. bookYear (In this library multiple years were saved, I believe that's different versions) - int[]
             * 8. bookPublisher - String
             * 9. Language - String[]
             * 10. Page Numbers - int
             * 11. ISBN - String
             * 12. File Type Saved - String[]
             */
            for (Element repository : repositories) {
                count ++;

                //Fetching the book from the list
                Elements links = repository.select("a[href]");
                String url = links.attr("href");
                url = "https://lk1lib.org" + url;
                String urlToScrape = url + "/?language=Java";
                Document tempBook = Jsoup.connect(urlToScrape).get(); // going for that book page

                //Book Cover
                Elements bookCover = tempBook.getElementsByClass("details-book-cover-content");
                Elements imgLinks = bookCover.select("a[href]");
                String imgUrl = imgLinks.attr("href");

                //Book Details hold the other details on book
                Elements bookDetails = tempBook.getElementsByClass("col-sm-9");
                //Book Title
                String bookTitle = bookDetails.select("h1").text();
                //Book authors
                String authors = bookDetails.select("i").text();
                //Book Description
                String bookDescription = "";
                try {
                    bookDescription = tempBook.getElementById("bookDescriptionBox").text();
                }
                catch (Exception e2){
                    bookDescription = "";
                }

                //Book Categories
                Elements bookCategoriesClass = tempBook.getElementsByClass("bookProperty property_categories");
                String[] bookCategories = bookCategoriesClass.select("div.property_value ").text().split(" ");

                //Book Years
                Elements bookYearClass = tempBook.getElementsByClass("bookProperty property_year");
                int[] bookYear = new int[]{};

                try {
                    bookYear = Stream.of(bookYearClass.select("div.property_value ").text().split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                }catch (NumberFormatException ignored){

                }

                //Book Publisher
                Elements bookPublisherClass = tempBook.getElementsByClass("bookProperty property_publisher");
                String bookPublisher = bookPublisherClass.select("div.property_value ").text();

                //Book Languages
                Elements bookLanguageClass = tempBook.getElementsByClass("bookProperty property_language");
                String[] bookLanguage = bookLanguageClass.select("div.property_value ").text().split(" ");

                //Book Pages
                Elements bookPagesClass = tempBook.getElementsByClass("bookProperty property_pages");
                String bookPages = bookPagesClass.select("div.property_value ").text();
                int bookPagesNumber = 0;
                //System.out.println("bookPages " + bookPages);
                /*
                 * Some book page count is stored as 123 / 456 pattern
                 * have to extract the total count
                 */
                Pattern pattern = Pattern.compile("/", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(bookPages);
                boolean matchFound = matcher.find();
                if (bookPages.isEmpty()){ //When book page count is not recorded stored a 0
                    //System.out.println("empty");
                    bookPagesNumber = 0;
                }
                else if(matchFound){
                    //System.out.println("In regex "+ bookPages);
                    bookPages = bookPages.replaceAll(" ", "");
                    int[] bookPagesArray = Stream.of(bookPages.split("/"))
                            .mapToInt(token -> Integer.parseInt(token))
                            .toArray();
                    bookPagesNumber = bookPagesArray[1];
                }
                else{
                    //System.out.println("out regex" + bookPages);
                    bookPagesNumber = Integer.parseInt(bookPages);
                }

                //ISBN
                Elements bookISBNClass = tempBook.getElementsByClass("bookProperty property_isbn 13");
                String bookISBN = bookISBNClass.select("div.property_value ").text();

                //Available Book Files
                Elements bookFileClass = tempBook.getElementsByClass("bookProperty property__file");
                String[] bookFile = bookFileClass.select("div.property_value ").text().split(" ");

                if (bookDescription.length()>1000){
                    bookDescription = bookDescription.trim().substring(0,1000);
                }

                if (authors.length()>1000){
                    authors = authors.substring(0,1000).trim();
                }

                BookScrape bookToAdd = (BookScrape) BookScrape.builder()
                        .author(authors)
                        .title(bookTitle)
                        .publisher(bookPublisher)
                        .description(bookDescription)
                        .isbn(bookISBN)
                        .build();

                try {
                    bookToAdd.setPublishYear(bookYear[0]);
                }catch (ArrayIndexOutOfBoundsException e){
                    log.warn("No Publish Year found");
                }

                bookToAdd.setImageUrl(imgUrl);

                log.info("Scrape {}\n{}",count,bookToAdd);

                books.add(bookToAdd);
            }

            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            log.error("Exception in Scraper",e);
        }finally {
            running=false;
            log.info("Scraper Completed");
        }
        return books;
    }

    public boolean isRunning(){
        return running;
    }

}
