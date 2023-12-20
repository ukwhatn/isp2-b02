import java.util.Collection;

public class Note {
	private String id;
	private Actor actor;
	private String body;
	private Collection<Like> likeCollection;
	
	private Note(Actor actor, String body) {
		this.actor = actor;
		this.body = body;
	}
	
	public static Note create(Actor actoe, String body) {
		return new Note(actoe, body);
	}
}
