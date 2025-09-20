import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rickmark.seriesviewmanager.data.firebase_connection.authentication.FirebaseUserAuthentication
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

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

        // Mock overload con Activity
        `when`(mockTask.addOnCompleteListener(any(AppCompatActivity::class.java), any()))
            .thenReturn(mockTask)
        `when`(mockTask.addOnFailureListener(any(AppCompatActivity::class.java), any()))
            .thenReturn(mockTask)

        // Mock overload sin Activity (para evitar NullPointerException)
        `when`(mockTask.addOnCompleteListener(any())).thenReturn(mockTask)
        `when`(mockTask.addOnFailureListener(any())).thenReturn(mockTask)

        firebaseAuthClass = FirebaseUserAuthentication()
    }

    @After
    fun tearDown() {
        firebaseAuthStaticMock.close()
    }

    @Test
    fun `getCurrentUser returns user when logged in`() {
        `when`(mockAuth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn("12345")

        val result = firebaseAuthClass.getCurrentUser()

        assertNotNull(result)
        assertEquals("12345", result?.uid)
    }

    @Test
    fun `getCurrentUser returns null when no user exists`() {
        `when`(mockAuth.currentUser).thenReturn(null)

        val result = firebaseAuthClass.getCurrentUser()

        assertNull(result)
    }

    @Test
    fun `logoutUser calls signOut when user exists`() {
        `when`(mockAuth.currentUser).thenReturn(mockUser)

        firebaseAuthClass.logoutUser()

        verify(mockAuth).signOut()
    }

    @Test
    fun `logoutUser does not call signOut when no user exists`() {
        `when`(mockAuth.currentUser).thenReturn(null)

        firebaseAuthClass.logoutUser()

        verify(mockAuth, never()).signOut()
    }

    @Test
    fun `createUser calls createUserWithEmailAndPassword`() {
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString()))
            .thenReturn(mockTask)

        firebaseAuthClass.createUser("test@mail.com", "123456") {}
        verify(mockAuth).createUserWithEmailAndPassword("test@mail.com", "123456")
    }

    @Test
    fun `loginUser calls signInWithEmailAndPassword`() {
        `when`(mockAuth.signInWithEmailAndPassword(anyString(), anyString()))
            .thenReturn(mockTask)

        firebaseAuthClass.loginUser("test@mail.com", "123456") {}

        firebaseAuthClass.loginUser(null, "123456") {}

        verify(mockAuth, times(1))
            .signInWithEmailAndPassword(anyString(), anyString())
    }
}
