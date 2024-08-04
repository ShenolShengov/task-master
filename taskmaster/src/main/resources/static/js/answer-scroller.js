let invalidDataScroller = document.getElementById("invalidDataScroll");
let addedAnswerScroller = document.getElementById("addedAnswerScroll");
if (invalidDataScroller != null) {
    invalidDataScroller.scrollIntoView({block: "end", behavior: "smooth"});
} else if (addedAnswerScroller != null) {
    addedAnswerScroller.scrollIntoView({block: "end", behavior: "smooth"});
}