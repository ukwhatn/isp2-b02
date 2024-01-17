const getNotes = () => {
    const notesElement = document.getElementById('notes');
    fetch('/isp2/notes')
        .then(response => response.json())
        .then(json => {
            // jsonを逆順に
            json = json.reverse();
            for (let note in json) {
                note = json[note];
                notesElement.innerHTML += `
                <div class="bg-light p-2 border-2 border-dark mb-2">
                        <h3>
                            <a href="#" onclick="getNote(${note.id})" class="text-decoration-none">
                                ${note.title}
                            </a>
                        </h3>
                        <p>
                            ${note.content}
                        </p>
                    </div>
                `;
            }
        });
};

const getNote = (id) => {
    fetch(`/isp2/note?id=${id}`)
        .then(response => response.json())
        .then(json => {
            // モーダルに値をセット
            document.getElementById('noteModalTitle').textContent = json.title;
            document.getElementById('noteModalContent').textContent = json.content;
            // モーダル表示
            const modal = new bootstrap.Modal(document.getElementById('noteModal'));
            modal.show();
        });
}

document.addEventListener('DOMContentLoaded', function () {
    getNotes();
});