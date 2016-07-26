package fireminder.fireminder.addnote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static fireminder.fireminder.addnote.NotesController.NoteListView;
import static fireminder.fireminder.addnote.NotesController.NotesRepository;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NotesControllerTest {

  @Mock
  NoteListView noteListView;

  @Mock
  NotesRepository notesRepository;


  @Captor
  ArgumentCaptor<NotesRepository.OnLoadedCallback> listenerArgumentCaptor;

  NotesController notesController;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    notesController = new NotesController(noteListView, notesRepository);
  }

  @Test
  public void populate_actualEntries_happyPath() throws Exception {
    notesController.populate();
    verify(notesRepository, times(1)).getAll(listenerArgumentCaptor.capture());
    listenerArgumentCaptor.getValue().onLoaded(Collections.singletonList(new NotesController.Note()));
    verify(noteListView, times(1)).clearList();
    verify(noteListView, times(1)).addNote(Matchers.anyListOf(NotesController.Note.class));
  }

  @Test
  public void populate_withNullEntriesDoesNotPopulateView() throws Exception {
    notesController.populate();
    verify(notesRepository, times(1)).getAll(listenerArgumentCaptor.capture());
    listenerArgumentCaptor.getValue().onLoaded(null);
    verify(noteListView, times(1)).clearList();
    verify(noteListView, times(1)).addNote(null);

  }
}