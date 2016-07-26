package fireminder.fireminder.addnote;

import java.util.List;

public class NotesController {

  NoteListView noteListView;
  NotesRepository notesRepository;

  public NotesController(NoteListView noteListView, NotesRepository notesRepository) {
    this.noteListView = noteListView;
    this.notesRepository = notesRepository;
  }

  public interface NoteListView {
    void clearList();

    void addNote(List<Note> notes);
  }

  public interface NotesRepository {
    void getAll(OnLoadedCallback callback);

    interface OnLoadedCallback {
      void onLoaded(List<Note> notes);
    }
  }

  public static class Note {

  }

  public void populate() {
    noteListView.clearList();
    notesRepository.getAll(new NotesRepository.OnLoadedCallback() {
      @Override
      public void onLoaded(List<Note> notes) {
        noteListView.addNote(notes);
      }
    });


  }
}
