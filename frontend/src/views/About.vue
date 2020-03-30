<template>
  <div v-cloak @drop.prevent="addFile" @dragover.prevent>
    <h2>File Upload {Drag them over}</h2>
    <ul>
      <li v-for="file in files">
        {{ file.name }} ({{file.size}} byte)
        <button @click="removeFile(file)" title="Remove">X</button>
      </li>
    </ul>
    <button :disabled="uploadDisabled" @click="upload">Upload</button>
    <b-button @click="testDown">TEST DOWNLOAD</b-button>
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
        this.$http.post('/api/file/download', 'test.xlsx',{
          headers:{
            'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
          }
        }).then(res => {
          console.log(res)
          console.log(res.data)
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
