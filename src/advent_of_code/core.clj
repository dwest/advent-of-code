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
           zero-count 0
           part-two 0]
      ;; (pprint/pprint {:curr curr
      ;;                 :direction direction
      ;;                 :clicks (Integer/parseInt clicks)
      ;;                 :zero-count zero-count
      ;;                 :part-two part-two})
      (let [c (Integer/parseInt clicks)
            delta
            (condp = direction
              "L" (* -1 c)
              "R" c)
            new
            (-> delta
                (+ curr)
                (mod 100))

            zero?
            (if (= new 0)
              1
              0)

            new-zero?
            (if (and (not= curr 0)
                     (= new 0))
              1
              0)

            crossed?
            (cond
              ;; was zero and < 100 clicks
              (and (= curr 0)
                   (< c 100))
              0

              ;; no remainder
              (and (= new 0)
                   (> delta 100))
              (abs (quot (+ delta curr)
                            100))

              (and (= new 0)
                   (< delta -100)
                   (= (mod (abs delta) 100)
                      curr))
              (+ new-zero?
                 (abs (quot (+ delta curr)
                            100)))

              ;; went out of bounds?
              (or (< (+ delta curr) 0)
                  (> (+ delta curr) 100))
              (max 1
                   (abs (quot (+ delta curr)
                              100)))

              :else new-zero?)
            ln (.readLine input)]
        (if-not (nil? ln)
          (do
            ;; (prn
            ;;  {:curr curr
            ;;   :new new
            ;;   :direction direction
            ;;   :clicks c
            ;;   :zero? zero?
            ;;   :zero-count (+ zero-count zero?)
            ;;   :crossed? crossed?
            ;;   :part-two (+ part-two crossed?)})
            (prn
             (format "%02d %s %3d = %02d; zero? %d zs %04d xs %02d pt %04d"
                     curr (if (= direction "L") "-" "+") c new
                     zero? (+ zero-count zero?)
                     crossed? (+ part-two crossed?)))
            ;; {:curr curr
            ;;   :new new
            ;;   :direction direction
            ;;   :clicks c
            ;;   :zero? zero?
            ;;   :zero-count (+ zero-count zero?)
            ;;   :crossed? crossed?
            ;;   :part-two (+ part-two crossed?)})
            (recur
             new
             (-> (re-seq #"([LR])([0-9]*)" ln)
                 first)
             (+ zero-count zero?)
             (+ part-two crossed?)))
          (prn (format "The password is: %d; part-two: %d" zero-count (+ part-two crossed?))))))))

(defn -main
  "Dispatch based on CLI flags"
  [mode day & args]
  ;; (pprint/pprint {:mode mode
  ;;                 :day day
  ;;                 :args args})
  (day-01 "inputs/day01/prod/stdin.txt"))
