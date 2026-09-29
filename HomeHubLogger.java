/**
 * Section 5: Use Cases — Plain, Concrete Class
 *
 * HomeHubLogger is a plain concrete class because there is only one kind of hub logger
 * with no variation expected. No family of related-but-different types exists, so
 * neither an abstract class nor an interface is needed.
 */
public class HomeHubLogger {
    public void log(String message) {
        System.out.println("[HUB] " + message);
    }
}
