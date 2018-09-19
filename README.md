# USF
*Undead(Game)StorageFormat*
## USF-File format – parser for Java

[![Build Status](https://travis-ci.com/kimbtech/USF.svg?branch=master)](https://travis-ci.com/kimbtech/USF)
[![Coverage Status](https://proxy.5d7.eu/coveralls/?repo=kimbtech%2FUSF&branch=master)](https://coveralls.io/github/kimbtech/USF?branch=master)

USF is a JSON-like file format allowing to store basic data types in Lists,
Pairs and multiple nesting of these.

See the [Wiki](https://github.com/kimbtech/USF/wiki) for examples and [JavaDoc](https://kimbtech.github.io/USF/) for
documentation.

## Data-Types
All USF-Data-Types are subclasses of the main USF-Data-Type called `Atom`.

- Basic Data Types
  - String
  - Boolean
  - Integer
  - Real
    - a double data type
- Structures
  - List
    - combining multiple Atoms
  - Pair
    - combining two Atoms
- Higher Level Types  
  - MapList
    - combining multiple Key-Value pairs


