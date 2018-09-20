# USF
*Undead(Game)StorageFormat*
## USF-File format – parser for Java

[![Build Status](https://travis-ci.com/kimbtech/USF.svg?branch=master)](https://travis-ci.com/kimbtech/USF)
[![Coverage Status](https://proxy.5d7.eu/coveralls/?repo=kimbtech%2FUSF&branch=master)](https://coveralls.io/github/kimbtech/USF?branch=master)

USF is a JSON-like file format allowing to store basic data types in Lists,
Pairs and multiple nesting of these.

See the [Wiki](https://github.com/KIMB-technologies/USF/wiki) for examples and [JavaDoc](https://KIMB-technologies.github.io/USF/) for
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

## USF Format
The normal USF Format is a one-line string, which contains all the data.

```
[true,[false,100,20,-12,{\"test\":{\"a\":false}},{{:}:{[true,true]:-12}}],=1.0=,=-11.02=]
```
For debugging and other cases one can use the human friendly output.
(Method `toHumFrieUSF()` instead of `toUSF()` and `parseHumFrie()` instead of `parse()`)

```
[
	true,
	[
		false,
		100,
		20,
		-12,
		{
			"test"
		:
			{
				"a"
			:
				false
			}
		},
		{
			{:}
		:
			{
				[
					true,
					true
				]
			:
				-12
			}
		}
	],
	=1.0=,
	=-11.02=
]
```

	 
