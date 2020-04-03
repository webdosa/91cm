class CommonClass {
  replacemsg(originContent) {
      let content = ''
      content = originContent.replace(/&lt;p&gt;/gim, '<p>')
      content = content.replace(/&lt;\/p&gt;/gim, '</p>')
      return content
  }
  replaceErrorMsg(){
    let array = originContent.split("\n")
    let content = ''
    for (let i in array) {
      content += '<p>' + array[i] + '</p>'
    }
    return content.replace(/ /gi, '&nbsp;')
  }

  replacemsgForPreview(originContent) {
    let array = originContent.split("&lt;/p&gt;&lt;p&gt;")
    let content = ''
    for (let i in array) {
      content += array[i] + ' '
    }
    content = content.replace('&lt;/p&gt;', '')
    content = content.replace('&lt;p&gt;', '')
    console.log(content)
    return content
  }

  byteCount(s, b, i, c) {
    for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 4 ? (c >> 7 ? 2 : 1) : 2) ;
    return b
  }

  byteLimit(length) {
    if (length > 30000) {
      alert('최대 30000byte까지 입력이 가능합니다.')
      return false
    } else {
      return true
    }
  }

}

export default new CommonClass()
