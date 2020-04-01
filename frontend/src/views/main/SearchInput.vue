<template>
    <div style="position: relative;" v-if="$store.state.isSearchMode">
        <i style="position:absolute;left: 15px;top: calc(50% - 12px);" class="im im-magnifier"></i>
        <a @click="up"><i style="position: absolute; right: 50px; top: calc(50% - 12px);" class="im im-angle-up"></i></a>
        <a @click="down"><i style="position: absolute; right: 15px; top: calc(50% - 12px);" class="im im-angle-down"></i></a>
            <b-form-input
            @keydown.esc.exact="toggleSearchMode"
            @keydown.enter.exact="search"
            placeholder="Search..."
            style="height: 80px;padding-left: 50px; padding-right: 90px;"
            autofocus
            ></b-form-input>
    </div>
</template>
<script>
export default {
    props: ['cursorPoint','wrapperEl','msgArray'],
    name: 'SearchInput',
    data() {
      return {
          searchResultList:null,
          offset: 0,
          index: 0,
          oldlength: 0,
          isGetMsgByUp:false
      }
    },
    mounted () {
        this.$nextTick(() => {
          this.wrapperEl = document.querySelector('.c-c-wrapper')
          console.log(this.wrapperEl)
      })
    },
    methods:{
      search(e){
        this.index=0
        this.oldlength=0
        e.preventDefault()
        this.$store.commit('setSearchText',e.target.value)
        this.$nextTick(() => {
            this.searchStart()
        })
      },
      toggleSearchMode (e) {
        console.log('searchmode close')
        this.$store.commit('setSearchText','')
        if (this.searchResultList != null) {
            console.log(this.searchResultList)
            this.searchResultList[this.index].classList.remove("addhighlight")
        }
        this.index=0
        this.oldlength=0
        this.$store.state.isSearchMode = !this.$store.state.isSearchMode
      },
      up: function() {
           if (0 < this.index) {
                this.searchResultList[this.index].classList.remove("addhighlight");
                this.index -= 1;
                this.scrollToSearchEl()
           }else{
               if(this.cursorPoint.empty == false){
                    this.searchResultList[this.index].classList.remove("addhighlight");
                    this.oldlength = this.searchResultList.length;
                    this.isGetMsgByUp = true
                    this.$emit('getMessage')
                    // this.$nextTick(() => {
                    //     this.searchStart();
                    // });
               }
           }
      },
      down() {
          if (this.searchResultList.length - 1 > this.index) {
        this.searchResultList[this.index].classList.remove("addhighlight");
        this.index += 1;
        this.scrollToSearchEl()
      }
      },
      searchStart () {
        console.log(document.querySelectorAll(".highlight"))
        this.searchResultList = document.querySelectorAll(".highlight")
        if (this.oldlength === 0) {
         this.index = this.searchResultList.length - 1
         this.scrollToSearchEl()
        }else{
            this.index = this.searchResultList.length - this.oldlength - 1
            console.log(this.searchResultList.length)
            console.log(this.index)
            this.scrollToSearchEl()
        }
      },
      scrollToSearchEl () {
        this.offset = this.searchResultList[this.index].offsetTop
        this.searchResultList[this.index].classList.add("addhighlight")
        console.log('없을거같당..22')
        console.log(this.wrapperEl)
        this.wrapperEl.scrollTo(0, this.offset)
      }
    },
    watch: {
        msgArray:function(){
            if(this.$store.state.isSearchMode && this.isGetMsgByUp){
                console.log('변경됨')
                this.isGetMsgByUp = false
                this.searchStart()
            }
        }
    }
}
</script>