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
$func = $_POST["func"];
if ($func == "regis") {
    register($conn);
}elseif ($func == "login") {
    login($conn);
}elseif ($func == "getuser") {
    getuser($conn);
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
