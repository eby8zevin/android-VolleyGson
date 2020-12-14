<?php
//mendefinisikan konstanta
 define('HOST', 'localhost');
 define('USER', 'username');
 define('PASS', 'password');
 define('DB', 'dbname');

//membuat koneksi dengan database
 $koneksi = mysqli_connect(HOST, USER, PASS, DB) or die(mysqli_errno());
?>
