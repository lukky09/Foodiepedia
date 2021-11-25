<?php
$host = "localhost";
$username = "root";
$password = "";
$dbname = "db_foodiepedia";

$conn = mysqli_connect($host, $username, $password, $dbname);
if (!$conn) {
    die("Connection Failed: " . mysqli_connect_error());
}

//buat ngeset function
switch ($_POST["func"]) {
    case "regis":
        register($conn);
      break;
    case "login":
        login($conn);
      break;
    case "getuser":
        getuser($conn);
      break;
    case "updateuser":
        updateuser($conn);
      break;
    case "getapprovedings":
        getingredients($conn);
      break;
  } 

function register($conn)
{
    $username = $_POST["user"];
    $password = $_POST["pass"];
    $name = $_POST["name"];
    $sql = "SELECT count(*) FROM user WHERE user_username = '$username'";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    if ($result[0] == 0) {
        $sql_insert = "INSERT INTO user (user_username,user_viewedname,user_password) VALUES ('$username','$name', '$password')";
        $query = mysqli_query($conn, $sql_insert);
        if ($query) {
            $message = "Register Berhasil!";
        } else {
            $message = "Register Gagal";
        }
    } else {
        $message = "Pengguna sudah ada!";
    }
    echo $message;
}

function login($conn)
{
    $username = $_POST["user"];
    $password = $_POST["pass"];
    $sql = "SELECT * FROM user WHERE user_username = '$username'";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    if ($result) {
        if ($password == $result[3]) {
            $response["userid"] = $result[0];
            $response["message"] = "Login Berhasil!";
            $response["isadmin"] = $result[4];
        } else {
            $response["userid"] = -1;
            $response["message"] = "Password Salah";
        }
    } else {
        $response["userid"] = -1;
        $response["message"] = "User Tidak Ditemukan";
    }
    echo json_encode($response);

}

function getuser($conn)
{
    $id = $_POST["userid"];
    $sql = "SELECT * FROM user WHERE user_id = $id";
    $result = mysqli_fetch_row(mysqli_query($conn, $sql));
    $response["nama"] = $result[2];
    $response["pass"] = $result[3];
    echo json_encode($response);
}

function updateuser($conn)
{
    $id = $_POST["userid"];
    $nama = $_POST["name"];
    $succ = 0;
    if(mysqli_query($conn, "UPDATE user SET user_viewedname='$nama' WHERE user_id=$id")){
        $succ+=1;
    }
    if($_POST["ispass"] == "y"){
        $pass = $_POST["pass"];
        if(mysqli_query($conn, "UPDATE user SET user_password='$pass' WHERE user_id=$id")){
            $succ+=1;
        }
    }
    $response["code"] = $succ;
    echo json_encode($response);
}

function getingredients($conn)
{
    $result = mysqli_query($conn, "SELECT * FROM bahans WHERE bahan_isapproved = 1 ORDER BY bahan_nama ASC");
    while ($row = mysqli_fetch_row($result)) {
        $bahan[] = $row[1];
    }
    echo json_encode($bahan);
}