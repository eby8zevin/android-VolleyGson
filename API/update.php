<?php
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
require_once('koneksi.php');

$id = $_POST['id'];
	
$nim = $_POST['nim'];
$nama = $_POST['nama'];
$alamat = $_POST['alamat'];
$ttl = $_POST['date'];
$status = $_POST['status'];

$getdata = mysqli_query($koneksi, "SELECT * FROM tugas_data WHERE id_data='$id'");
$rows = mysqli_num_rows($getdata);

$update = "UPDATE tugas_data SET nim_data='$nim', 
		                         nama_data='$nama', 
		                         alamat_data='$alamat',
		                         ttl_data='$ttl',
		                         status_data='$status'
		                         WHERE id_data='$id'";
		                         
$exequery = mysqli_query($koneksi, $update);
$response = array();

if($rows > 0) 
{
    if($exequery)
    {
        $response['status_kode'] = 1;
        $response['status_pesan'] = "Updated Success";
    } else {
        $response['status_kode'] = 0;
        $response['status_pesan'] = "Updated Failed";
    }
} else {
    $response['status_kode'] = 0;
    $response['status_pesan'] = "Updated Failed, Not data selected";
}

header('Content-type: text/javascript');
echo json_encode($response, JSON_PRETTY_PRINT);
?>
