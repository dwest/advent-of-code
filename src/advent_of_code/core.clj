(ns advent-of-code.core
  (:require [clojure.pprint :as pprint]
            [clojure.java.io :as jio]
            [clojure.string :as s])
  (:gen-class))

(defn get-file
  [mode day]
  (condp = (keyword mode)
    :dev  (format "input/day%02d/test/stdin.txt")
    :test (format "input/day%02d/test/stdin.txt")
    :prod (format "input/day%02d/prod/stdin.txt")))

(defn day-01
  [f]
  (with-open [input (jio/reader f)]
    (loop [curr 50 ; start at 50 per directions
           [_, direction, clicks] (-> (re-seq #"([LR])([0-9]*)" (.readLine input))
                                      first)
           zero-count 0]
      (pprint/pprint {:curr curr
                      :direction direction
                      :clicks (Integer/parseInt clicks)
                      :zero-count zero-count
                      })
      ;; (when-not (= steps 3)
      ;;   (recur
      ;;    curr
      ;;    (-> (re-seq #"([LR])([0-9]*)" (.readLine input))
      ;;        first)
      ;;    (inc steps)))

      (let [c (Integer/parseInt clicks)
            new
            (->
             (condp = direction
              "L" (* -1 c)
              "R" c)
             (+ curr)
             (mod 100))
            ln (.readLine input)]
        (if-not (nil? ln)
          (recur
           new
           (-> (re-seq #"([LR])([0-9]*)" ln)
               first)
           (if (= new 0)
             (inc zero-count)
             zero-count))
          (prn (format "The password is: %d" zero-count)))))))

(defn -main
  "I don't do a whole lot."
  [mode day & args]
  ;; (pprint/pprint {:mode mode
  ;;                 :day day
  ;;                 :args args})
  (day-01 "inputs/day01/prod/stdin.txt"))
