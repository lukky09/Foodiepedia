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
    case "getalluser":
        getalluser($conn);
        break;
    case "setban":
        setban($conn);
        break;
    case "updateuser":
        updateuser($conn);
        break;
    case "getapprovedings":
        getingredients($conn);
        break;
    case "addresep":
        addrecipe($conn);
        break;
    case "addresepings":
        addingtorecipe($conn);
        break;
    case "adding":
        addingredient($conn);
        break;
    case "getresep":
        getresep($conn);
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

function getalluser($conn)
{
    $sql = "SELECT * FROM user WHERE user_isadmin = 0";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $arruser = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["id"] = $row["user_id"];
            $data["nama"] = $row["user_viewedname"];
            $data["pass"] = $row["user_password"];
            $data["is_banned"] = $row["user_isbanned"];
            $arruser[$ctr] = $data;
            $ctr++;
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["datauser"] = $arruser;
    } else {
        $response["code"] = -3;
        $response["message"] = "No Data";
    }
    echo json_encode($response);
}

function setban($conn)
{
    $id = $_POST["userid"];
    $ban = $_POST["isbanned"];
    if ($ban == 0) {
        mysqli_query($conn, "UPDATE user SET user_isbanned=1 WHERE user_id=$id");
        $response["message"] = "Ban Berhasil";
    } else {
        mysqli_query($conn, "UPDATE user SET user_isbanned=0 WHERE user_id=$id");
        $response["message"] = "Unban Berhasil";
    }
    echo json_encode($response);
}

function updateuser($conn)
{
    $id = $_POST["userid"];
    $nama = $_POST["name"];
    $succ = 0;
    if (mysqli_query($conn, "UPDATE user SET user_viewedname='$nama' WHERE user_id=$id")) {
        $succ += 1;
    }
    if ($_POST["ispass"] == "y") {
        $pass = $_POST["pass"];
        if (mysqli_query($conn, "UPDATE user SET user_password='$pass' WHERE user_id=$id")) {
            $succ += 1;
        }
    }
    $response["code"] = $succ;
    echo json_encode($response);
}

function getingredients($conn)
{
    $result = mysqli_query($conn, "SELECT * FROM bahans ORDER BY bahan_nama ASC");
    while ($row = mysqli_fetch_row($result)) {
        $isi["nama"] = $row[1];
        $isi["app"] = $row[2];
        $bahan[] = $isi;
    }
    echo json_encode($bahan);
}

function addrecipe($conn)
{
    $id = $_POST["id"];
    $nm = $_POST["nama"];
    $desc = $_POST["desc"];
    $ada = $_POST["ada"];
    mysqli_query($conn, "INSERT INTO reseps (resep_nama,user_id,resep_desc,resep_isapproved) VALUES ('$nm',$id,'$desc',$ada)");
    echo mysqli_fetch_row(mysqli_query($conn, "SELECT * FROM reseps WHERE resep_nama = '$nm'"))[0];
}

function addingredient($conn)
{
    $nm = $_POST["nama"];
    mysqli_query($conn, "INSERT INTO bahans (bahan_nama,bahan_isapproved) VALUES ('$nm',0)");
}

function addingtorecipe($conn)
{
    $id = $_POST["id"];
    $nm = $_POST["nama"];
    $jum = $_POST["jum"];
    $idbahan = mysqli_fetch_row(mysqli_query($conn, "SELECT * FROM bahans WHERE bahan_nama = '$nm'"))[0];
    mysqli_query($conn, "INSERT INTO bahanresep VALUES ($idbahan,$id,'$jum')");
    echo mysqli_fetch_row(mysqli_query($conn, "SELECT count(*) FROM bahanresep WHERE resep_id = $id"))[0];
}

function getresep($conn)
{
    $sql = "SELECT * FROM reseps";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) {
        $data = array();
        $reseps = array();
        $ctr = 0;
        while ($row = mysqli_fetch_array($result)) {
            $data["resep_id"] = $row["resep_id"];
            $data["resep_nama"] = $row["resep_nama"];
            $data["resep_isapproved"] = $row["resep_isapproved"];
            $reseps[$ctr] = $data;
            $ctr++;
        }
        mysqli_free_result($result);
        $response["code"] = 1;
        $response["message"] = "Get Data Successful";
        $response["dataresep"] = $reseps;
    } else {
        $response["code"] = -1;
        $response["message"] = "Tidak Ada Request Resep";
    }
}
