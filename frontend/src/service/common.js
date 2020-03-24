class CommonClass{
  replacemsg(originContent) {
      let array = originContent.split("\n")
      let content = ''
      for (let i in array) {
        content += '<p>' + array[i] + '</p>'
      }
      return content.replace(/ /gi, '&nbsp;')
    }
    replacemsgForPreview(originContent){
      let array = originContent.split("</p><p>")
      let content = ''
      for (let i in array) {
        content +=  array[i] + ' '
      }
      return content
    }
  
}
export default new CommonClass()