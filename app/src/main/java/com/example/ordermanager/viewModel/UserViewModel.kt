package com.example.ordermanager.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ordermanager.model.Order
import com.example.ordermanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class UserViewModel : ViewModel() {

    private val collectionUser="Users"
    private val collectionEmployee="Employees"
    private val db = FirebaseFirestore.getInstance()

    var fireAuth = FirebaseAuth.getInstance()

    val authLD : MutableLiveData<Auth> = MutableLiveData()
    val userLD : MutableLiveData<User> = MutableLiveData()
    val userTypeAuthLD : MutableLiveData<USER> = MutableLiveData()
    val errMessLD : MutableLiveData<String> = MutableLiveData()


    enum class Auth{
        AUTHENTICATED, UNAUTHENTICATED
    }
    enum class USER{
        ISUSER, ISADMIN
    }

    init {
        if (fireAuth.currentUser != null){
            authLD.value= Auth.AUTHENTICATED
            if (userTypeAuthLD.value != USER.ISUSER){
                userTypeAuthLD.value= USER.ISUSER
            }else{
                userTypeAuthLD.value= USER.ISADMIN
            }
        }
        else{
            authLD.value= Auth.UNAUTHENTICATED
        }
    }

    fun adminLogin(email : String, pass : String){
        fireAuth.signInWithEmailAndPassword(email,pass)
            .addOnSuccessListener {
                val uid = fireAuth.currentUser!!.uid
                db.collection(collectionUser)
                    .document(uid)
                    .get()
                    .addOnSuccessListener {
                    if (it.exists()){
                        authLD.value= Auth.AUTHENTICATED

                        //check user type
                        if (it.getString("isUser") != null){
                            userTypeAuthLD.value=USER.ISUSER
                        }else{
                            userTypeAuthLD.value=USER.ISADMIN
                        }
                        /*else if(it.getString("isAdmin") !=null){
                            userTypeAuthLD.value=USER.ISADMIN
                        }*/
                        //
                    }

                    else{
                        errMessLD.value= "You are not an Admin"
                        fireAuth.signOut()
                    }
                }.addOnFailureListener {
                    errMessLD.value= "You are not an Admin"
                        fireAuth.signOut()
                    }

            }.addOnFailureListener {
                errMessLD.value=it.localizedMessage
            }
    }

    fun getAllEmployee() : LiveData<List<User>>{

        val userListLD : MutableLiveData<List<User>> = MutableLiveData()
        db.collection(collectionUser).addSnapshotListener { value, error ->
            if (error !=null){
                errMessLD.value=error.localizedMessage
                return@addSnapshotListener
            }
            val temUserList = mutableListOf<User>()
            for (doc in value!!.documents){
                val currentUser= doc.id
                if (fireAuth.currentUser?.uid != currentUser){
                    temUserList.add(doc.toObject(User::class.java)!!)
                }

            }
            userListLD.value=temUserList
        }

        return userListLD
    }

    fun getUserById() : LiveData<User> {

        var uId= fireAuth.currentUser!!.uid
        if (uId== fireAuth.currentUser!!.uid){
            db.collection(collectionUser).document(uId).addSnapshotListener { value, error ->
                if (error !=null){
                    errMessLD.value=error.localizedMessage
                    return@addSnapshotListener
                }

                userLD.value= value!!.toObject(User::class.java)
            }
        }

        return userLD
    }
}