<template>
  <div>
    <img src="../assets/images/fileIcon/txt_icon.png" alt="이미지를 찾을 수 없음"/>
  </div>
</template>
<script>
  export default {
    name: 'About',
    data() {
      return {
        files: []
      }
    },
    computed: {
      uploadDisabled() {
        return this.files.length === 0;
      }
    },
    updated() {
    },
    methods: {
      testDown: function () {
        let text = 'test.xlsx'
        this.$http.get('/api/file/download/44dbf5cb6ff146898f8e7692df5e947e',
          {
            'fileName': '장비 현황.xlsx'
          }, {
            responseType: 'blob'
          }
        )
          .then(res => {
            console.log(res)
            const url = window.URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.href = url;
            const contentDisposition = res.headers['content-disposition']
            console.log(contentDisposition)
            let fileName = 'unKnown'
            if (contentDisposition) {
              const fileNameMatch = contentDisposition.match(/filename\*?=['"]?(?:UTF-\d['"]*)?([^;\r\n"']*)['"]?;?/)
              console.log(fileNameMatch)
              console.log(fileNameMatch.length)
              if (fileNameMatch.length === 2) {
                fileName = fileNameMatch[1];
                fileName = decodeURI(fileName)
              }
              console.log(decodeURIComponent(fileName))
              console.log(fileName)
            }
            link.setAttribute('download', fileName)
            document.body.appendChild(link)
            link.click()
            link.remove()
            window.URL.revokeObjectURL(url)
          })
      },
      addFile(e) {
        let droppedFiles = e.dataTransfer.files;
        if (!droppedFiles) return;
        ([...droppedFiles]).forEach(f => {
          this.files.push(f);
        })
      },
      removeFile(file) {
        this.files = this.files.filter(f => {
          return f != file;
        })
      },
      upload() {
        let formData = new FormData()
        this.files.forEach((f, x) => {
          formData.append('file' + (x + 1), f)
        })
        console.log(formData.get('file1'))
      }
    }

  }

</script>
