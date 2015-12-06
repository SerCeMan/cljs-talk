(ns talk
  (:require [hiccup.core :refer [html]]))


(comment

  ; Clojure crash course

  ;
  ;; There is literal data:
  ;

  ; number
  1.23

  ; string
  "foo"

  ; keyword (like strings, but used as map keys)
  :foo

  ; vector (array)
  [:bar 3.14 "hello"]

  ; map (associative array)
  {:msg "hello" :pi 3.14 :primes [2 3 5 7 11 13]}

  ; set (distinct elements)
  #{:bar 3.14 "hello"}

  ; symbol (represents a named value)
  foo

  ; list (represents a "call")
  (foo :bar 3.14)






  ;
  ;; Evaluation
  ;

  1.23

  [:bar 3.14 "hello"]

  ; A symbol evaluates to the value bound to it
  foo


  ;A list evaluates to the return value of a "call".

  (+ 1 2 3)
  (= 1 2)
  (if (= 1 1)
    "y"
    "n")
















  ;
  ;; Calls
  ;


  ; String concatenate function
  (str "Hello " "World")

  (def a 3)
  (def b 4)
  (def c 5)

  ; Arithmetic functions
  (= a b)                                                   ; equality (true or false)
  (+ a b)                                                   ; sum
  (- a b)                                                   ; difference
  (* a b c)                                                 ; product
  (< a b c)                                                 ; true if a < b < c

  ; Evaluation Steps
  (+ k (* 2 4))                                             ; assume k evalutes to 3
  (+ 3 (* 2 4))                                             ; (* 2 4) evaluates to 8
  (+ 3 8)                                                   ; (+ 3 8) evalutes to 11
  11


  ; Control flow
  (if (= a b c)                                             ; <-- determines if a=b=c
    (foo 1)                                                 ; <-- only evaluated if true
    (bar 2)                                                 ; <-- only evaluated if false
    )

  ; define k as 3
  (def k 3)                                                 ; <-- notice that k is not evaluated here

  ; make a greeting function
  (fn [username]                                            ; <-- expected parameters vector
    (str "Hello " username))

  ; oops, give the function a name
  (def greet (fn [username]
               (str "Hello " username)))

  (greet "Bob")                                             ; => "Hello Bob"


  (defn greet [name]
    (str "Hello " name))

  (def greet (partial str "Hello "))




  (html
    [:div {:class "well"}
     [:h1 {:class "text-info"} "Hello Hiccup and AngularJS"]
     [:div {:class "row"}
      [:div {:class "col-lg-2"}]
      [:div {:class "col-lg-4"}]]
     [:hr]
     [:h1 {:class "text-success"} "Hello {{yourName}}!"]])

  )