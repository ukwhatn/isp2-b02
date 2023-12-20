import java.util.Collection;

public class Actor {
	private String id;
	private String name;
	private String hashedPassword;
	private Collection<Note> notesCollection;
	private Collection<Follower> followersCollection;
	
	/**
	 * アクターIDを返す。
	 * @return
	 */
	public String getById() {
		return id;
	}
	
	/**
	 * アクターの名前を返す
	 * @return
	 */
	public String getByName() {
		return name;
	}
	
	public static void register(String id, String name, String password) {
		//データをデータベースに保存
		/*(ここに記述)*/
	}
	
	public static void validatePassword(String id, String password) {
		//パスワードを照会
		/*(ここに記述)*/
	}
	
	/**
	 * ノートを生成する
	 */
	public void createNote(String body) {
		Note newNote = Note.create(this, body);
	}
}
