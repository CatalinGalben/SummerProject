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
    return this.angularFireAuth.auth.signInWithEmailAndPassword(user, pass);
  }

  logout() {
    return this.angularFireAuth.auth.signOut();
  }

}
