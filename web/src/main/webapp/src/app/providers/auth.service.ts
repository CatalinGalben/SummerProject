import { Injectable } from '@angular/core';
import { AngularFireAuth } from "angularfire2/auth";
import * as firebase from "firebase";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public angularFireAuth: AngularFireAuth) {

  }

  loginWithGoogle() {


    return this.angularFireAuth.auth.signInWithPopup(new firebase.auth.GoogleAuthProvider)

  }

  loginWithEmail(user: string, pass: string) {



    return this.angularFireAuth.auth.signInWithEmailAndPassword(user, pass)


    /*this.angularFireAuth.auth.onAuthStateChanged(function (user) {
      if(user){
        if(!user.emailVerified){
          firebase.auth().signOut().then(function() {
            // Sign-out successful.
          }).catch(function(error) {
            // An error happened.
          });
          alert("Email not verified!");
          }
      }
      else{
        alert("Login failed");
      }
    });
    */

  }

  logout() {
    return this.angularFireAuth.auth.signOut()

  }
  createNewAccount(email:string, password:string)
  {
    this.angularFireAuth.auth.createUserWithEmailAndPassword(email, password)
      .catch(err => alert(err.message));
  }

}
