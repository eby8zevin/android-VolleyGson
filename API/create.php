<?php
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
include_once('koneksi.php');

$nim = $_GET['nim'];
$nama = $_GET['nama'];
$alamat = $_GET['alamat'];
$ttl = $_GET['date'];
$status = $_GET['status'];

$insert = "INSERT INTO tugas_data (nim_data, 
                                   nama_data, 
                                   alamat_data, 
                                   ttl_data, 
                                   status_data)
                            VALUES (
                                   '$nim',
                                   '$nama', 
                                   '$alamat',
                                   '$ttl', 
                                   '$status')";

$exeinsert = mysqli_query($koneksi, $insert);
$response = array();

if($exeinsert) {
  $response['status_kode'] =1;
  $response['status_pesan'] = "Success! Data Inserted";
} else {
  $response['status_kode'] =0;
  $response['status_pesan'] = "Failed! Data Not Inserted";
}

header('Content-type: text/javascript');
echo json_encode($response, JSON_PRETTY_PRINT);
?>
