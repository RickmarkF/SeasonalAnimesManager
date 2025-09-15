import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rickmark.seriesviewmanager.data.firebase_connection.authentication.FirebaseUserAuthentication
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.MockedStatic

class FirebaseUserAuthenticationTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockUser: FirebaseUser

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var firebaseAuthClass: FirebaseUserAuthentication

    private lateinit var firebaseAuthStaticMock: MockedStatic<FirebaseAuth>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        firebaseAuthStaticMock = mockStatic(FirebaseAuth::class.java)
        firebaseAuthStaticMock.`when`<FirebaseAuth> { FirebaseAuth.getInstance() }
            .thenReturn(mockAuth)

        `when`(mockTask.addOnCompleteListener(any(AppCompatActivity::class.java), any()))
            .thenReturn(mockTask)
        `when`(mockTask.addOnFailureListener(any(AppCompatActivity::class.java), any()))
            .thenReturn(mockTask)

        firebaseAuthClass = FirebaseUserAuthentication()
    }

    @After
    fun tearDown() {
        firebaseAuthStaticMock.close()
    }

    @Test
    fun `getCurrentUser devuelve usuario cuando está logueado`() {
        `when`(mockAuth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn("12345")

        val result = firebaseAuthClass.getCurrentUser()

        assertNotNull(result)
        assertEquals("12345", result?.uid)
    }

    @Test
    fun `getCurrentUser devuelve null cuando no hay usuario`() {
        `when`(mockAuth.currentUser).thenReturn(null)

        val result = firebaseAuthClass.getCurrentUser()

        assertNull(result)
    }

    @Test
    fun `logoutUser llama a signOut cuando hay usuario`() {
        `when`(mockAuth.currentUser).thenReturn(mockUser)

        firebaseAuthClass.logoutUser()

        verify(mockAuth).signOut()
    }

    @Test
    fun `logoutUser no llama a signOut cuando no hay usuario`() {
        `when`(mockAuth.currentUser).thenReturn(null)

        firebaseAuthClass.logoutUser()

        verify(mockAuth, never()).signOut()
    }

    @Test
    fun `createUser llama a createUserWithEmailAndPassword`() {
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString()))
            .thenReturn(mockTask)

        firebaseAuthClass.createUser("test@mail.com", "123456"){}
        verify(mockAuth).createUserWithEmailAndPassword("test@mail.com", "123456")
    }

    @Test
    fun `loginUser llama a signInWithEmailAndPassword`() {

        `when`(mockAuth.signInWithEmailAndPassword(anyString(), anyString()))
            .thenReturn(mockTask)

        firebaseAuthClass.loginUser("test@mail.com", "123456"){}

        firebaseAuthClass.loginUser(null, "123456"){}

        verify(mockAuth,times(1))
            .signInWithEmailAndPassword(anyString(), anyString())
    }
}
