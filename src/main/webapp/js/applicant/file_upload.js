/**
 * Created by JooLiu on 2016/8/3.
 */

var uploader = new plupload.Uploader({
    runtimes : 'html5,html4',
    browse_button : 'attach_file', // you can pass in id...
    container: document.getElementById('container'), // ... or DOM Element itself
    url : "/examples/upload",
    filters : {
        max_file_size : '10mb',
        mime_types: [
            {title : "Image files", extensions : "jpg,gif,png"},
            {title : "Zip files", extensions : "zip"}
        ]
    },
    init: {
        PostInit: function() {
            document.getElementById('filelist').innerHTML = '';
            document.getElementById('uploadfiles').onclick = function() {
                uploader.start();
                return false;
            };
        },

        FilesAdded: function(up, files) {
            plupload.each(files, function(file) {
                document.getElementById('filelist').innerHTML += '<a id="' + file.id + '"  href="javascript:;">'
                    + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></a>';
            });
        },

        UploadProgress: function(up, file) {
            document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent
                + "%</span>";
        },

        Error: function(up, err) {
            document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
        }
    }
});
uploader.init();