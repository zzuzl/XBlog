<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>XBlog</title>
</head>
<body>
欢迎来到XBlog!
${success},${msg}
<input type="file" id="file" name="file"/>

<script src="${root}/resource/js/jquery-1.11.2.js"></script>
<script src="${root}/resource/js/firebase.js"></script>
<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyBqSmIjf8CZvFvbawF44NTiikDIY6vM9Ag",
        authDomain: "myfirebaseproject-8c327.firebaseapp.com",
        databaseURL: "https://myfirebaseproject-8c327.firebaseio.com",
        storageBucket: "myfirebaseproject-8c327.appspot.com"
    };
    firebase.initializeApp(config);

    // Get a reference to the storage service, which is used to create references in your storage bucket
    var storage = firebase.storage();
    var auth = firebase.auth();

    // Create a storage reference from our storage service
    var storageRef = storage.ref();

    // Create a child reference
    var imagesRef = storageRef.child('images');
    // imagesRef now points to 'images'

    // Child references can also take paths delimited by '/'
    var spaceRef = storageRef.child('images/space.jpg');
    // spaceRef now points to "images/space.jpg"
    // imagesRef still points to "images"

    $(function () {
        // Sign the user in anonymously since accessing Storage requires the user to be authorized.
        auth.signInAnonymously().then(function (user) {
            console.log('Anonymous Sign In Success', user);
            document.getElementById('file').disabled = false;
        }).catch(function (error) {
            console.error('Anonymous Sign In Error', error);
        });

        $("#file").change(function (event) {
            var file = event.target.files[0];
            console.log(file);
            var metadata = {
                'contentType': file.type
            };

            // Push to child path.
            var uploadTask = storageRef.child('images').put(file, metadata);
            // Listen for errors and completion of the upload.
            uploadTask.on('state_changed', null, function (error) {
                console.error('Upload failed:', error);
            }, function () {
                console.log('Uploaded', uploadTask.snapshot.totalBytes, 'bytes.');
                console.log(uploadTask.snapshot.metadata);
                var url = uploadTask.snapshot.metadata.downloadURLs[0];
                console.log('File available at', url);
            });
        });
    });
</script>
</body>
</html>
