@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackSearch")
    @GetMapping("/{query}")
    public ResponseEntity<GeneralResponse> search(@PathVariable String query) {
        CompletableFuture<Object> bookFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("http://book-service/books/" + query, Object.class));
        CompletableFuture<Object> authorFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("http://author-service/authors/" + query, Object.class));
        CompletableFuture<Object> detailsFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject("http://details/port", Object.class));

        return CompletableFuture.allOf(bookFuture, authorFuture, detailsFuture)
                .thenApply(v -> {
                    Map<String, Object> data = Map.of(
                            "book", bookFuture.join(),
                            "author", authorFuture.join(),
                            "details", detailsFuture.join()
                    );
                    return ResponseEntity.ok(new GeneralResponse("200", System.currentTimeMillis(), data));
                });
    }

    public ResponseEntity<GeneralResponse> fallbackSearch(String query) {
        Map<String, Object> data = Map.of("error", "Service is currently unavailable");
        return ResponseEntity.ok(new GeneralResponse("500", System.currentTimeMillis(), data));
    }
}
