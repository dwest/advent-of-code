(ns advent-of-code.core
  (:require [clojure.pprint :as pprint]
            [clojure.java.io :as jio])
  (:gen-class))

(defn get-file
  [mode day]
  nil)

(defn day-01
  [f]
  (with-open [input (jio/input-stream f)]
    
)

(defn -main
  "I don't do a whole lot."
  [mode day & args]
  (pprint/pprint {:mode mode
                  :day day
                  :args args}))
